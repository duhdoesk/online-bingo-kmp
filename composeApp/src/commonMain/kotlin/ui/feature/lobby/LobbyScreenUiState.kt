package ui.feature.lobby

import domain.feature.user.model.User
import domain.feature.user.model.mockUser
import domain.room.model.BingoRoom
import domain.room.model.mockBingoRoom

data class LobbyScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val availableRooms: List<BingoRoom> = emptyList(),
    val query: String = "",
    val user: User? = null,
    val isSubscribed: Boolean = false
)

fun mockLobbyScreenUiState(): LobbyScreenUiState {
    return LobbyScreenUiState(
        isLoading = false,
        availableRooms = listOf(mockBingoRoom()),
        query = "",
        user = mockUser()
    )
}
