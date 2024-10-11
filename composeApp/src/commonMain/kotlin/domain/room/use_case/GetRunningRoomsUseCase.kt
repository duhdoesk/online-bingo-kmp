package domain.room.use_case

import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.map

class GetRunningRoomsUseCase(
    private val bingoRoomRepository: BingoRoomRepository,
    private val mapRoomDTOToModelUseCase: MapRoomDTOToModelUseCase,
) {
    operator fun invoke(bingoType: BingoType) = bingoRoomRepository
        .getRunningRooms(bingoType)
        .map { list ->
            list.map { dto ->
                mapRoomDTOToModelUseCase(dto)
            }
        }
}