package data.room.repository

import data.room.model.bingoRoomDTOFromDocumentSnapshot
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BingoRoomRepositoryImpl(firestore: FirebaseFirestore) : BingoRoomRepository {

    private val collection = firestore.collection("rooms")

    override fun getRooms(bingoType: BingoType): Flow<List<BingoRoom>> =
        collection
            .where { "type" equalTo bingoType.name }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                }
            }

    override fun getNotStartedRooms(bingoType: BingoType): Flow<List<BingoRoom>> =
        collection
            .where { "state" equalTo RoomState.NOT_STARTED.name }
            .where { "type" equalTo bingoType.name }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                }
            }

    override fun getRunningRooms(bingoType: BingoType): Flow<List<BingoRoom>> =
        collection
            .where { "state" equalTo RoomState.RUNNING.name }
            .where { "type" equalTo bingoType.name }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                }
            }

    override fun flowRoomById(id: String): Flow<BingoRoom> =
        collection
            .document(id)
            .snapshots
            .map { documentSnapshot ->
                bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
            }

    override suspend fun getRoomById(id: String): Result<BingoRoom> {
        collection
            .document(id)
            .get()
            .let { documentSnapshot ->
                if (!documentSnapshot.exists) {
                    return Result.failure(NoSuchElementException())
                }

                try {
                    val room = bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                    return Result.success(room)
                } catch (e: Exception) {
                    return Result.failure(e)
                }
            }
    }

    override suspend fun createRoom(
        hostId: String,
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Result<String> {
        try {
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
                        "drawnCharactersIds" to emptyList<String>()
                    )
                )
            return Result.success(documentReference.id)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun joinRoom(roomId: String, userId: String): Result<Unit> {
        try {
            collection.document(roomId)
                .update(data = hashMapOf("players" to FieldValue.arrayUnion(userId)))

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateRoomState(roomId: String, state: String): Result<Unit> {
        try {
            collection
                .document(roomId)
                .update(data = hashMapOf("state" to state))
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun addRaffledCharacter(roomId: String, characterId: String): Result<Unit> {
        try {
            collection
                .document(roomId)
                .update(data = hashMapOf("drawnCharactersIds" to FieldValue.arrayUnion(characterId)))
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun addWinner(roomId: String, userId: String): Result<Unit> {
        try {
            collection
                .document(roomId)
                .update(data = hashMapOf("winners" to FieldValue.arrayUnion(userId)))
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
