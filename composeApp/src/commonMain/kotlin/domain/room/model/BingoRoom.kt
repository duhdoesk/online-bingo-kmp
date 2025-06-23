package domain.room.model

import domain.character.model.Character
import domain.feature.user.model.User
import domain.feature.user.model.mockUser
import domain.theme.model.Theme
import domain.theme.model.mockBingoThemeList
import kotlinx.datetime.LocalDateTime
import ui.feature.room.state.auxiliar.BingoState
import util.getLocalDateTimeNow

data class BingoRoom(
    val id: String,
    val host: User,
    val type: Type,
    val name: String,
    val maxWinners: Int,
    val privacy: RoomPrivacy,
    val raffled: List<String>,
    val state: BingoState,
    val players: List<String>,
    val winnersIds: List<String>,
    val createdAt: LocalDateTime
) {
    sealed class Type() {

        data class Themed(
            val theme: Theme,
            val characters: List<Character>
        ) : Type()

        data object Classic : Type()
    }
}

fun mockBingoRoom(): BingoRoom {
    return BingoRoom(
        id = "",
        host = mockUser(),
        type = BingoRoom.Type.Themed(
            theme = mockBingoThemeList().drop(1).first(),
            characters = emptyList()
        ),
        name = "Sala Mockada",
        maxWinners = 4,
        privacy = RoomPrivacy.Open,
        raffled = emptyList(),
        state = BingoState.entries.random(),
        players = emptyList(),
        winnersIds = emptyList(),
        createdAt = getLocalDateTimeNow()
    )
}
