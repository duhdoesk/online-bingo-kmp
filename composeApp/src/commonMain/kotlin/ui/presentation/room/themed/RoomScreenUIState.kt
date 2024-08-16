package ui.presentation.room.themed

import domain.character.model.Character
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.theme.model.BingoTheme
import domain.user.model.User

open class RoomScreenUIState(
    /**
     * Represents the screen loading state
     * */
    open val loading: Boolean,

    /**
     * Represents the list of players that joined the room
     * */
    open val players: List<User>,

    /**
     * Represents the theme of the bingo
     * */
    open val theme: BingoTheme?,

    /**
     * Represents the list of characters in the selected theme
     * */
    open val characters: List<Character>,

    /**
     * Represents the already raffled characters
     * */
    open val raffledCharacters: List<Character>,

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
     * Represents the bingo type
     * */
    open val bingoType: BingoType,

    /**
     * Represents the bingo state
     * */
    open val bingoState: RoomState,
)