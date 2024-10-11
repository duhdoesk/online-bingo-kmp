package ui.presentation.room.state.auxiliar

enum class BingoState {
    /**
     * Raffle has not started, and the host is waiting for the users to join the room
     */
    NOT_STARTED,

    /**
     * Raffle is in progress
     */
    RUNNING,

    /**
     * Bingo is already over
     */
    FINISHED,
}