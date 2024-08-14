package ui.presentation.room.host.state

import domain.character.model.Character
import domain.room.model.BingoType
import domain.room.model.RoomState
import domain.theme.model.BingoTheme
import domain.user.model.User
import ui.presentation.room.RoomScreenUIState

data class HostScreenUIState(
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
     * Represents the full list of characters of the selected theme
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
     * Represents a check if the host can raffle the next character or not
     * */
    val canRaffleNextCharacter: Boolean,
): RoomScreenUIState(
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
        val INITIAL = HostScreenUIState(
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
            canRaffleNextCharacter = true,
        )
    }
}