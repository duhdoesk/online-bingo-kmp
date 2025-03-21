package domain.room.useCase

import domain.room.model.BingoRoom
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FlowRoomByIdUseCase(
    private val roomRepository: BingoRoomRepository,
    private val mapRoomDTOToModelUseCase: MapRoomDTOToModelUseCase
) {
    operator fun invoke(roomId: String): Flow<BingoRoom> {
        return roomRepository.flowRoomById(roomId).map { dto ->
            mapRoomDTOToModelUseCase(dto)
        }
    }
}
