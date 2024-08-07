package domain.room.use_case

import domain.room.model.BingoRoom
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow

class GetRoomsUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(): Flow<List<BingoRoom>> = roomRepository.getRooms()
}