package domain.room.useCase

import domain.room.model.RoomState
import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class UpdateRoomStateUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String, state: RoomState): Flow<Resource<Unit>> {
        return roomRepository.updateRoomState(roomId = roomId, state = state.name)
    }
}
