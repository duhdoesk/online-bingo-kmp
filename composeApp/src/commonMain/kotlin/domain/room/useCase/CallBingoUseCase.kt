package domain.room.useCase

import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class CallBingoUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String, userId: String): Flow<Resource<Unit>> {
        return roomRepository.addWinner(roomId, userId)
    }
}
