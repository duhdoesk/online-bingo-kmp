package domain.room.useCase

import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class RaffleNextItemUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String, item: String): Flow<Resource<Unit>> {
        return roomRepository.addRaffledCharacter(roomId, item)
    }
}
