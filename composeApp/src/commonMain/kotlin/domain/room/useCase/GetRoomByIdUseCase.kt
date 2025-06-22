package domain.room.useCase

import domain.room.model.BingoRoom
import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetRoomByIdUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(roomId: String): Flow<Resource<BingoRoom>> {
        return roomRepository.getRoomById(roomId)
    }
}
