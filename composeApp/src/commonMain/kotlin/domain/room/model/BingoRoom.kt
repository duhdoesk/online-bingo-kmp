package domain.room.model

import ui.presentation.room.state.auxiliar.BingoState

data class BingoRoom(
    val id: String,
    val hostId: String,
    val type: BingoType,
    val name: String,
    val themeId: String?,
    val maxWinners: Int,
    val locked: Boolean,
    val password: String?,
    val raffled: List<String>,
    val state: BingoState,
    val players: List<String>,
    val winners: List<String>
)
