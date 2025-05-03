package ui.feature.room.state

import domain.feature.user.model.User
import ui.feature.room.state.auxiliar.BingoState
import ui.feature.room.state.auxiliar.BingoStyle
import ui.feature.room.state.auxiliar.DataState
import ui.feature.room.state.auxiliar.RaffleButtonState

data class RoomHostState(

    /**
     * Represents the data loading state
     * */
    val dataState: DataState,

    /**
     * Represents the type of bingo that is being played, along with its metadata
     */
    val bingoStyle: BingoStyle,

    /**
     * Represents the bingo state
     * */
    val bingoState: BingoState,

    /**
     * Represents the room name
     */
    val roomName: String,

    /**
     * Represents the host of the room
     */
    val host: User?,

    /**
     * Represents a list of players and their info
     */
    val players: List<User>,

    /**
     * Represents the list of winners
     */
    val winners: List<User>,

    /**
     * Represents the max amount of winners determined by the host
     */
    val maxWinners: Int,

    /**
     * Represents the items that has been already raffled
     */
    val raffledItems: List<String>,

    /**
     * Represents if host can raffle next item
     */
    val raffleButtonState: RaffleButtonState,

    /**
     * Represents any error that may occur
     */
    val hostScreenError: HostScreenError?
) {
    companion object {
        val INITIAL = RoomHostState(
            dataState = DataState.LOADING,
            bingoStyle = BingoStyle.Classic,
            bingoState = BingoState.NOT_STARTED,
            roomName = "",
            host = null,
            players = listOf(),
            winners = listOf(),
            maxWinners = 1,
            raffledItems = listOf(),
            raffleButtonState = RaffleButtonState.SUSPEND,
            hostScreenError = null
        )
    }
}

enum class HostScreenError {
    START, FINISH
}
