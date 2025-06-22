package domain.room.repository

import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface BingoRoomRepository {

    /** Returns a room given its ID */
    fun getRoomById(id: String): Flow<Resource<BingoRoom>>

    /** Returns all open rooms */
    fun getAvailableRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>>

    /** Returns all rooms that are not started yet given a Bingo Type */
    fun getNotStartedRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>>

    /** Returns all rooms that are already running given a Bingo Type */
    fun getRunningRooms(bingoType: BingoType): Flow<Resource<List<BingoRoom>>>

    /** Creates a new room and returns its ID */
    fun createRoom(
        hostId: String,
        name: String,
        privacy: RoomPrivacy,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Flow<Resource<String>>

    /** Joins a room */
    fun joinRoom(roomId: String, userId: String): Flow<Resource<Unit>>

    /** Updates room state */
    fun updateRoomState(roomId: String, state: String): Flow<Resource<Unit>>

    /** Adds a raffled character to the room */
    fun addRaffledCharacter(roomId: String, characterId: String): Flow<Resource<Unit>>

    /** Adds a winner to the room */
    fun addWinner(roomId: String, userId: String): Flow<Resource<Unit>>
}
