package ui.presentation.room.classic.play.state

import domain.room.model.RoomState
import domain.user.model.User
import ui.presentation.room.classic.ClassicRoomScreenUIState

class ClassicPlayScreenUIState(
    /**
     * Represents the screen loading state
     * */
    override val loading: Boolean,

    /**
     * Represents the list of players that joined the room
     * */
    override val players: List<User>,

    /**
     * Represents the list of characters in the selected theme
     * */
    override val numbers: List<Int>,

    /**
     * Represents the already raffled characters
     * */
    override val raffledNumbers: List<Int>,

    /**
     * Represents the max number of possible winners
     * */
    override val maxWinners: Int,

    /**
     * Represents the list of winners
     * */
    override val winners: List<User>,

    /**
     * Represents the name of the room
     * */
    override val roomName: String,

    /**
     * Represents the bingo state
     * */
    override val bingoState: RoomState,

    /**
     * Represents a check if the user is eligible to call bingo or not
     * */
    val canCallBingo: Boolean,

    /**
     * Represents a check if the user has called bingo already
     */
    val calledBingo: Boolean,

    /**
     * Represents the user's Bingo Card
     */
    val myCard: List<Int>,

    ) : ClassicRoomScreenUIState(
    loading,
    players,
    numbers,
    raffledNumbers,
    maxWinners,
    winners,
    roomName,
    bingoState
) {
    companion object {
        val INITIAL = ClassicPlayScreenUIState(
            loading = true,
            players = listOf(),
            numbers = listOf(),
            raffledNumbers = listOf(),
            maxWinners = 1,
            winners = listOf(),
            roomName = "",
            bingoState = RoomState.NOT_STARTED,
            canCallBingo = false,
            calledBingo = false,
            myCard = listOf(),
        )
    }
}