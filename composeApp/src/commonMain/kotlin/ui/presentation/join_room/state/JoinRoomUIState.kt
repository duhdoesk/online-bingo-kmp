package ui.presentation.join_room.state

import domain.room.model.BingoRoom

data class JoinRoomUIState(
    /**
     * Represents the screen loading state
     * */
    val loading: Boolean,

    /**
     * Represents the available rooms the user can join
     * */
    val rooms: List<BingoRoom>,
) {
    companion object {
        val INITIAL = JoinRoomUIState(
            loading = true,
            rooms = emptyList(),
        )
    }
}