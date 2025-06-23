package domain.room.useCase

import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetRunningRoomsUseCase(private val bingoRoomRepository: BingoRoomRepository) {
    operator fun invoke(bingoType: BingoType): Flow<Resource<List<BingoRoom>>> {
        return bingoRoomRepository.getRunningRooms(bingoType)
    }
}
