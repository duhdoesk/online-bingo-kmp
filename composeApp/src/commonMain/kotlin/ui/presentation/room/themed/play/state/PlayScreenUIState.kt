package ui.presentation.room.themed.play.state

import domain.character.model.Character
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.theme.model.BingoTheme
import domain.user.model.User
import ui.presentation.room.themed.RoomScreenUIState

data class PlayScreenUIState(
    /**
     * Represents the screen loading state
     * */
    override val loading: Boolean,

    /**
     * Represents the list of players that joined the room
     * */
    override val players: List<User>,

    /**
     * Represents the theme of the bingo
     * */
    override val theme: BingoTheme?,

    /**
     * Represents the list of characters in the selected theme
     * */
    override val characters: List<Character>,

    /**
     * Represents the already raffled characters
     * */
    override val raffledCharacters: List<Character>,

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
     * Represents the bingo type
     * */
    override val bingoType: BingoType,

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
    val myCard: List<Character>,
) : RoomScreenUIState(
    loading,
    players,
    theme,
    characters,
    raffledCharacters,
    maxWinners,
    winners,
    roomName,
    bingoType,
    bingoState,
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