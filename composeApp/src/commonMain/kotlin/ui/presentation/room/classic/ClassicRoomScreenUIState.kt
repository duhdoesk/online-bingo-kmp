package ui.presentation.room.classic

import domain.room.model.RoomState
import domain.user.model.User

open class ClassicRoomScreenUIState(
    /**
     * Represents the screen loading state
     * */
    open val loading: Boolean,

    /**
     * Represents the list of players that joined the room
     * */
    open val players: List<User>,

    /**
     * Represents the list of characters in the selected theme
     * */
    open val numbers: List<Int>,

    /**
     * Represents the already raffled characters
     * */
    open val raffledNumbers: List<Int>,

    /**
     * Represents the max number of possible winners
     * */
    open val maxWinners: Int,

    /**
     * Represents the list of winners
     * */
    open val winners: List<User>,

    /**
     * Represents the name of the room
     * */
    open val roomName: String,

    /**
     * Represents the bingo state
     * */
    open val bingoState: RoomState,
)