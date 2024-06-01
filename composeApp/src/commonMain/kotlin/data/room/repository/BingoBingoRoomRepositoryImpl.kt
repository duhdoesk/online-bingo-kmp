package data.room.repository

import data.room.model.BingoRoomDTO
import data.room.model.bingoRoomDTOFromModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BingoBingoRoomRepositoryImpl : BingoRoomRepository {

    private val firestore = Firebase.firestore
    private val collection = firestore.collection("rooms")

    override fun getRooms(): Flow<List<BingoRoom>> =
        collection
            .snapshots
            .map { query ->
                query.documents.map { document ->
                    document.data(BingoRoomDTO.serializer()).toModel()
                }
            }

    override fun getRoomById(id: String): Flow<BingoRoom> =
        collection
            .document(id)
            .snapshots
            .map { document ->
                document.data(BingoRoomDTO.serializer()).toModel()
            }

    override suspend fun createRoom(
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): String {
        return collection
            .add(
                data = hashMapOf(
                    "name" to name,
                    "locked" to locked,
                    "password" to password,
                    "maxWinners" to maxWinners,
                    "type" to type.name,
                    "themeId" to themeId,
                    "state" to "NOT_STARTED"
                )
            )
            .id
    }
}