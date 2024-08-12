package domain.room.use_case

import domain.room.repository.BingoRoomRepository

class RaffleNextCharacterUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(
        roomId: String,
        characterId: String,
    ): Result<Unit> {
        return roomRepository.addRaffledCharacter(roomId, characterId)
    }
}