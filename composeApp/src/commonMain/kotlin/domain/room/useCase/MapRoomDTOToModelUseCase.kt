package domain.room.useCase

import data.room.model.BingoRoomDTO
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import ui.presentation.room.state.auxiliar.BingoState

class MapRoomDTOToModelUseCase {
    operator fun invoke(dto: BingoRoomDTO): BingoRoom {
        val bingoType = when (dto.type) {
            "THEMED" -> BingoType.THEMED
            else -> BingoType.CLASSIC
        }

        val bingoState = when (dto.state) {
            "NOT_STARTED" -> BingoState.NOT_STARTED
            "RUNNING" -> BingoState.RUNNING
            else -> BingoState.FINISHED
        }

        return BingoRoom(
            id = dto.id,
            hostId = dto.hostId,
            type = bingoType,
            name = dto.name,
            maxWinners = dto.maxWinners,
            locked = dto.locked,
            password = dto.password,
            raffled = dto.drawnCharactersIds,
            state = bingoState,
            players = dto.players,
            winners = dto.winners,
            themeId = dto.themeId
        )
    }
}
