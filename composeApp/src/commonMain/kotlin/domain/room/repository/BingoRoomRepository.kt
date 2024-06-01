package domain.room.repository

import domain.room.model.BingoRoom
import domain.room.model.BingoType
import kotlinx.coroutines.flow.Flow

interface BingoRoomRepository {
    fun getRooms(): Flow<List<BingoRoom>>
    fun getRoomById(id: String): Flow<BingoRoom>
    suspend fun createRoom(
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): String
}