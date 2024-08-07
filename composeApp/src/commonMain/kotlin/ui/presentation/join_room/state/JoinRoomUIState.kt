package ui.presentation.join_room.state

import domain.room.model.BingoRoom

data class JoinRoomUIState(
    /**
     * Represents the screen loading state
     * */
    val loading: Boolean,

    /**
     * Represents the available and not started rooms the user can join
     * */
    val notStartedRooms: List<BingoRoom>,

    /**
     * Represents the available and already running rooms the user can join
     * */
    val runningRooms: List<BingoRoom>,

    /**
     * Represents the query being made by the user in the search box
     * */
    val query: String,
) {
    companion object {
        val INITIAL = JoinRoomUIState(
            loading = true,
            notStartedRooms = emptyList(),
            runningRooms = emptyList(),
            query = "",
        )
    }
}