package data.room.repository

import data.firebase.firebaseCall
import data.firebase.firebaseSuspendCall
import data.room.model.BingoRoomDTO
import data.room.model.bingoRoomDTOFromDocumentSnapshot
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.toMilliseconds
import domain.character.repository.CharacterRepository
import domain.feature.user.UserRepository
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.room.model.RoomState
import domain.room.repository.BingoRoomRepository
import domain.theme.repository.ThemeRepository
import domain.util.resource.Cause
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ui.feature.room.state.auxiliar.BingoState
import util.getLocalDateTimeNow

class BingoRoomRepositoryImpl(
    firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val characterRepository: CharacterRepository,
    private val themeRepository: ThemeRepository
) : BingoRoomRepository {

    private val collection = firestore.collection("rooms")

    override fun getRoomById(id: String): Flow<Resource<BingoRoom>> {
        return firebaseCall {
            collection
                .document(id)
                .snapshots
                .map { bingoRoomDTOFromDocumentSnapshot(it) }
        }
            .map { resource ->
                when (resource) {
                    is Resource.Failure -> resource
                    is Resource.Success -> buildBingoRoom(resource.data)
                }
            }
    }

    override fun getAvailableRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>> {
        return firebaseCall {
            collection
                .where { "state" notEqualTo RoomState.FINISHED.name }
                .where { "type" equalTo bingoType.name }
                .snapshots
                .map { querySnapshot ->
                    querySnapshot.documents.map { documentSnapshot ->
                        bingoRoomDTOFromDocumentSnapshot(documentSnapshot)
                    }
                }
        }
            .map { resource ->
                when (resource) {
                    is Resource.Failure -> resource
                    is Resource.Success -> {
                        Resource.Success(
                            resource.data.mapNotNull { buildBingoRoom(it).getOrNull() }
                        )
                    }
                }
            }
    }

    override fun getNotStartedRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>> {
        return firebaseCall {
            collection
                .where { "state" equalTo RoomState.NOT_STARTED.name }
                .where { "type" equalTo bingoType.name }
                .snapshots
                .map { querySnapshot ->
                    querySnapshot.documents.map { documentSnapshot ->
                        bingoRoomDTOFromDocumentSnapshot(documentSnapshot)
                    }
                }
        }
            .map { resource ->
                when (resource) {
                    is Resource.Failure -> resource
                    is Resource.Success -> {
                        Resource.Success(
                            resource.data.mapNotNull { buildBingoRoom(it).getOrNull() }
                        )
                    }
                }
            }
    }

    override fun getRunningRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>> {
        return firebaseCall {
            collection
                .where { "state" equalTo RoomState.RUNNING.name }
                .where { "type" equalTo bingoType.name }
                .snapshots
                .map { querySnapshot ->
                    querySnapshot.documents.map { documentSnapshot ->
                        bingoRoomDTOFromDocumentSnapshot(documentSnapshot)
                    }
                }
        }
            .map { resource ->
                when (resource) {
                    is Resource.Failure -> resource
                    is Resource.Success -> {
                        Resource.Success(
                            resource.data.mapNotNull { buildBingoRoom(it).getOrNull() }
                        )
                    }
                }
            }
    }

    override fun createRoom(
        hostId: String,
        name: String,
        privacy: RoomPrivacy,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Flow<Resource<String>> {
        val locked = privacy is RoomPrivacy.Private
        val password = if (privacy is RoomPrivacy.Private) privacy.password else null

        return firebaseSuspendCall {
            val documentReference = collection
                .add(
                    data = hashMapOf(
                        "hostId" to hostId,
                        "name" to name,
                        "locked" to locked,
                        "password" to password,
                        "maxWinners" to maxWinners,
                        "type" to type.name,
                        "themeId" to themeId,
                        "state" to "NOT_STARTED",
                        "winners" to emptyList<String>(),
                        "players" to emptyList<String>(),
                        "drawnCharactersIds" to emptyList<String>(),
                        "createdAt" to getLocalDateTimeNow().toString()
                    )
                )
            documentReference.id
        }
    }

    override fun joinRoom(roomId: String, userId: String): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
            collection.document(roomId)
                .update(data = hashMapOf("players" to FieldValue.arrayUnion(userId)))
        }
    }

    override fun updateRoomState(roomId: String, state: String): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
            collection
                .document(roomId)
                .update(data = hashMapOf("state" to state))
        }
    }

    override fun addRaffledCharacter(roomId: String, characterId: String): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
            collection
                .document(roomId)
                .update(data = hashMapOf("drawnCharactersIds" to FieldValue.arrayUnion(characterId)))
        }
    }

    override fun addWinner(roomId: String, userId: String): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
            collection
                .document(roomId)
                .update(data = hashMapOf("winners" to FieldValue.arrayUnion(userId)))
        }
    }

    private suspend fun buildBingoRoom(dto: BingoRoomDTO): Resource<BingoRoom> {
        val bingoType = if (dto.type == "CLASSIC" || dto.themeId == null) {
            BingoRoom.Type.Classic
        } else {
            val theme = themeRepository
                .getThemeById(dto.themeId)
                .map { it.getOrNull() }
                .first()

            if (theme == null) return Resource.Failure(Cause.INVALID_THEME)

            val characters = characterRepository
                .getThemeCharacters(dto.themeId)
                .map { it.getOrNull() ?: emptyList() }
                .first()

            BingoRoom.Type.Themed(
                theme = theme,
                characters = characters
            )
        }

        val host = userRepository
            .getUserById(dto.hostId)
            .map { it.getOrNull() }
            .first()

        if (host == null) return Resource.Failure(Cause.USER_NOT_FOUND)

        val privacy =
            if (!dto.locked || dto.password == null) {
                RoomPrivacy.Open
            } else {
                RoomPrivacy.Private(dto.password)
            }

        return Resource.Success(
            BingoRoom(
                id = dto.id,
                host = host,
                type = bingoType,
                name = dto.name.trim(),
                maxWinners = dto.maxWinners,
                privacy = privacy,
                raffled = dto.raffled,
                state = BingoState.valueOf(dto.state),
                players = dto.players,
                winnersIds = dto.winners,
                createdAt = Instant.fromEpochMilliseconds(dto.createdAt.toMilliseconds().toLong()).toLocalDateTime(TimeZone.UTC)
            )
        )
    }
}
