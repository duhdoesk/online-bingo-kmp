package domain.room.use_case

import domain.room.model.BingoRoom
import domain.room.repository.BingoRoomRepository

class GetRoomByIdUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(roomId: String): Result<BingoRoom> {
        return roomRepository.getRoomById(roomId)
    }
}