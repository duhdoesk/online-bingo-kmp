package domain.room.model

import domain.card.model.Card
import domain.user.model.User

data class BingoRoom(
    val id: String,
    val hostId: String,
    val type: BingoType,
    val name: String,
    val themeId: String?,
    val maxWinners: Int,
    val locked: Boolean,
    val password: String?,
    val drawnCharactersIds: List<String>,
    val state: RoomState,
    val winners: List<String>
)