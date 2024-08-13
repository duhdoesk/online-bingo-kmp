package ui.presentation.room.play.state

import domain.card.model.Card
import domain.character.model.Character
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.theme.model.BingoTheme
import domain.user.model.User

data class PlayScreenUIState(
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
     * Represents the list of characters in the selected theme
     * */
    val characters: List<Character>,

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
    val myCard: List<Character>,
) {
    companion object {
        val INITIAL = PlayScreenUIState(
            loading = true,
            players = listOf(),
            theme = null,
            characters = listOf(),
            raffledCharacters = listOf(),
            maxWinners = 0,
            winners = listOf(),
            roomName = "",
            bingoType = BingoType.THEMED,
            bingoState = RoomState.NOT_STARTED,
            canCallBingo = false,
            calledBingo = false,
            myCard = emptyList(),
        )
    }
}