package ui.presentation.host.state

import domain.character.model.Character
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.theme.model.BingoTheme
import domain.user.model.User

data class HostScreenUIState(
    /**
     * Represents the screen loading state
     * */
    val loading: Boolean,

    /**
     * Represents the list of players that joined the room
     * */
    val players: List<User>,

    /**
     * Represents the theme of the bingo
     * */
    val theme: BingoTheme?,

    /**
     * Represents the full list of characters of the selected theme
     * */
    val themeCharacters: List<Character>,

    /**
     * Represents the already raffled characters
     * */
    val raffledCharacters: List<Character>,

    /**
     * Represents the max number of possible winners
     * */
    val maxWinners: Int,

    /**
     * Represents the list of winners
     * */
    val winners: List<User>,

    /**
     * Represents the name of the room
     * */
    val roomName: String,

    /**
     * Represents the bingo type
     * */
    val bingoType: BingoType,

    /**
     * Represents the bingo state
     * */
    val bingoState: RoomState,

    /**
     * Represents the bingo state
     * */
    val canRaffleNextCharacter: Boolean,
) {
    companion object {
        val INITIAL = HostScreenUIState(
            loading = true,
            players = listOf(),
            theme = null,
            themeCharacters = listOf(),
            raffledCharacters = listOf(),
            maxWinners = 0,
            winners = listOf(),
            roomName = "",
            bingoType = BingoType.THEMED,
            bingoState = RoomState.NOT_STARTED,
            canRaffleNextCharacter = true,
        )
    }
}