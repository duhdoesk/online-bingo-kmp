package data.room.model

import data.card.model.CardDTO
import data.user.model.UserDTO
import domain.card.model.Card
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.user.model.User
import kotlinx.serialization.Serializable

@Serializable
class BingoRoomDTO(
    private val id: String,
    private val hostId: String,
    private val type: String,
    private val name: String,
    private val themeId: String?,
    private val maxWinners: Int,
    private val locked: Boolean,
    private val password: String?,
    private val drawnCharactersIds: List<String>,
    private val state: String,
    private val winners: List<String>
) {
    fun toModel(): BingoRoom {
        val modelType = when (type) {
            "CLASSIC" -> BingoType.CLASSIC
            else -> BingoType.THEMED
        }

        val modelState = when (state) {
            "NOT_STARTED" -> RoomState.NOT_STARTED
            "RUNNING" -> RoomState.RUNNING
            else -> RoomState.FINISHED
        }

        return BingoRoom(
            id = id,
            hostId = hostId,
            type = modelType,
            name = name,
            themeId = themeId,
            maxWinners = maxWinners,
            locked = locked,
            password = password,
            drawnCharactersIds = drawnCharactersIds,
            state = modelState,
            winners = winners
        )
    }
}

fun bingoRoomDTOFromModel(room: BingoRoom): BingoRoomDTO =
    room.run {
        BingoRoomDTO(
            id = id,
            hostId = hostId,
            type = type.name,
            name = name,
            themeId = themeId,
            maxWinners = maxWinners,
            locked = locked,
            password = password,
            drawnCharactersIds = drawnCharactersIds,
            state = state.name,
            winners = winners
        )
    }