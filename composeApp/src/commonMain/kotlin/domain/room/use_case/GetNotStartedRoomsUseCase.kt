package domain.room.use_case

import domain.room.repository.BingoRoomRepository

class GetNotStartedRoomsUseCase(private val bingoRoomRepository: BingoRoomRepository) {
    operator fun invoke() = bingoRoomRepository.getNotStartedRooms()
}