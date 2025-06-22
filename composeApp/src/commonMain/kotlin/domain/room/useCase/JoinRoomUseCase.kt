package domain.room.useCase

import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class JoinRoomUseCase(private val bingoRoomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String, userId: String): Flow<Resource<Unit>> {
        return bingoRoomRepository.joinRoom(roomId = roomId, userId = userId)
    }
}
