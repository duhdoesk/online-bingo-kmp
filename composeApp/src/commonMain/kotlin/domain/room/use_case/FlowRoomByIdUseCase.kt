package domain.room.use_case

import domain.room.model.BingoRoom
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow

class FlowRoomByIdUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String): Flow<BingoRoom> = roomRepository.flowRoomById(roomId)
}