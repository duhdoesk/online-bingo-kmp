package domain.room.use_case

import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository

class GetNotStartedRoomsUseCase(private val bingoRoomRepository: BingoRoomRepository) {
    operator fun invoke(bingoType: BingoType) = bingoRoomRepository.getNotStartedRooms(bingoType)
}