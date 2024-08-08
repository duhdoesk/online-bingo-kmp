package domain.room.repository

import dev.gitlive.firebase.firestore.DocumentSnapshot
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import kotlinx.coroutines.flow.Flow

interface BingoRoomRepository {
    fun getRooms(): Flow<List<BingoRoom>>

    fun getNotStartedRooms(): Flow<List<BingoRoom>>

    fun getRunningRooms(): Flow<List<BingoRoom>>

    fun flowRoomById(id: String): Flow<BingoRoom>

    suspend fun getRoomById(id: String): Result<BingoRoom>

    suspend fun createRoom(
        hostId: String,
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Flow<DocumentSnapshot>

    suspend fun joinRoom(roomId: String, userId: String): Result<Unit>
}