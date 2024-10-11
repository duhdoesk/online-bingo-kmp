package domain.room.use_case

import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRoomsUseCase(
    private val roomRepository: BingoRoomRepository,
    private val mapRoomDTOToModelUseCase: MapRoomDTOToModelUseCase,
) {
    operator fun invoke(bingoType: BingoType): Flow<List<BingoRoom>> {
        return roomRepository.getRooms(bingoType).map { list ->
            list.map { dto ->
                mapRoomDTOToModelUseCase(dto)
            }
        }
    }
}