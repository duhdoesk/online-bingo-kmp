package ui.feature.room.state.auxiliar

sealed class UserRole {
    /**
     * User that created the room and is it's host
     */
    data class Host(val canRaffleNext: Boolean) : UserRole()

    /**
     * User that joined the room to play bingo
     */
    data class Player(
        val canCallBingo: Boolean,
        val hasCalledBingo: Boolean,
        val cardState: CardState
    ) : UserRole()
}
