package domain.room.use_case

import domain.room.repository.BingoRoomRepository

class GetRunningRoomsUseCase(private val bingoRoomRepository: BingoRoomRepository) {
    operator fun invoke() = bingoRoomRepository.getRunningRooms()
}