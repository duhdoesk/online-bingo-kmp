package ui.feature.lobby

import domain.feature.user.model.User
import domain.feature.user.model.mockUser
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.mockBingoRoom

sealed interface LobbyScreenUiState {
    /** Represents the idle (initial) state of the UI */
    data object Idle : LobbyScreenUiState

    /** Represents the failure state of the UI */
    data class Failure(val errorMessage: String? = null) : LobbyScreenUiState

    /** Represents the success state of the UI */
    data class Success(
        val isLoading: Boolean = false,
        val type: BingoType = BingoType.THEMED,
        val availableRooms: List<BingoRoom> = emptyList(),
        val query: String = "",
        val user: User? = null,
        val isSubscribed: Boolean = false
    ) : LobbyScreenUiState
}

fun mockLobbyScreenUiState(): LobbyScreenUiState.Success {
    return LobbyScreenUiState.Success(
        isLoading = false,
        availableRooms = listOf(mockBingoRoom()),
        query = "",
        user = mockUser()
    )
}
