package data.room.repository

import data.room.model.BingoRoomDTO
import data.room.model.bingoRoomDTOFromDocumentSnapshot
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BingoRoomRepositoryImpl(
    private val firestore: FirebaseFirestore
) : BingoRoomRepository {

    private val collection = firestore.collection("rooms")

    override fun getRooms(): Flow<List<BingoRoom>> =
        collection
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                }
            }

    override fun getNotStartedRooms(): Flow<List<BingoRoom>> =
        collection
            .where { "state" equalTo RoomState.NOT_STARTED.name }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                }
            }

    override fun getRunningRooms(): Flow<List<BingoRoom>> =
        collection
            .where { "state" equalTo RoomState.RUNNING.name }
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
                if (documentSnapshot.exists) {
                    try {
                        val room = bingoRoomDTOFromDocumentSnapshot(documentSnapshot).toModel()
                        return Result.success(room)
                    } catch (e: Exception) {
                        return Result.failure(e)
                    }

                } else {
                    return Result.failure(NoSuchElementException())
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
    ) =
        collection
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
            .snapshots

    override suspend fun joinRoom(roomId: String, userId: String): Result<Unit> {
        try {
            collection.document(roomId)
                .update(data = hashMapOf("players" to FieldValue.arrayUnion(userId)))

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
