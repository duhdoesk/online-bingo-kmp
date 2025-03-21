package domain.room.useCase

import domain.room.repository.BingoRoomRepository

class CallBingoUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(
        roomId: String,
        userId: String
    ): Result<Unit> {
        return roomRepository.addWinner(roomId, userId)
    }
}
