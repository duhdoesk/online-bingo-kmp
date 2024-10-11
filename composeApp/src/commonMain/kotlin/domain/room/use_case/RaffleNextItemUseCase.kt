package domain.room.use_case

import domain.room.repository.BingoRoomRepository

class RaffleNextItemUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(
        roomId: String,
        item: String,
    ): Result<Unit> {
        return roomRepository.addRaffledCharacter(roomId, item)
    }
}