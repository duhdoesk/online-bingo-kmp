package domain.room.repository

import domain.room.model.BingoRoom
import domain.room.model.BingoType
import kotlinx.coroutines.flow.Flow

interface BingoRoomRepository {
    fun getRooms(bingoType: BingoType): Flow<List<BingoRoom>>

    fun getNotStartedRooms(bingoType: BingoType): Flow<List<BingoRoom>>

    fun getRunningRooms(bingoType: BingoType): Flow<List<BingoRoom>>

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
    ): Result<String>

    suspend fun joinRoom(roomId: String, userId: String): Result<Unit>

    suspend fun updateRoomState(roomId: String, state: String): Result<Unit>

    suspend fun addRaffledCharacter(roomId: String, characterId: String) : Result<Unit>

    suspend fun addWinner(roomId: String, userId: String): Result<Unit>
}