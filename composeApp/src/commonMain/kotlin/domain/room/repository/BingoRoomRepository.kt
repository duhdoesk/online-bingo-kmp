package domain.room.repository

import data.room.model.BingoRoomDTO
import domain.room.model.BingoType
import kotlinx.coroutines.flow.Flow

interface BingoRoomRepository {
    fun getRooms(bingoType: BingoType): Flow<List<BingoRoomDTO>>

    fun getNotStartedRooms(bingoType: BingoType): Flow<List<BingoRoomDTO>>

    fun getRunningRooms(bingoType: BingoType): Flow<List<BingoRoomDTO>>

    fun flowRoomById(id: String): Flow<BingoRoomDTO>

    suspend fun getRoomById(id: String): Result<BingoRoomDTO>

    suspend fun createRoom(
        hostId: String,
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Result<String>

    suspend fun joinRoom(roomId: String, userId: String): Result<Unit>

    suspend fun updateRoomState(roomId: String, state: String): Result<Unit>

    suspend fun addRaffledCharacter(roomId: String, characterId: String): Result<Unit>

    suspend fun addWinner(roomId: String, userId: String): Result<Unit>
}
