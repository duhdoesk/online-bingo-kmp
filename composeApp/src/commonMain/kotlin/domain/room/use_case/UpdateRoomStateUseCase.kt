package domain.room.use_case

import domain.room.model.RoomState
import domain.room.repository.BingoRoomRepository

class UpdateRoomStateUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(
        roomId: String,
        state: RoomState,
    ): Result<Unit> {
        return roomRepository.updateRoomState(
            roomId = roomId,
            state = state.name
        )
    }
}