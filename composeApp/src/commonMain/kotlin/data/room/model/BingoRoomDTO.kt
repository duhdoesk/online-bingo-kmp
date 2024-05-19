package data.room.model

import domain.card.model.Card
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.user.model.User

class BingoRoomDTO(
    val id: String,
    val hostId: String,
    val type: String,
    val name: String,
    val themeId: String?,
    val maxWinners: Int,
    val locked: Boolean,
    val password: String?,
    val drawnCharactersIds: List<String>,
    val state: String,
    val players: List<User>,
    val winners: List<String>,
    val cards: List<Card>
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
            players = players,
            winners = winners,
            cards = cards
        )
    }
}