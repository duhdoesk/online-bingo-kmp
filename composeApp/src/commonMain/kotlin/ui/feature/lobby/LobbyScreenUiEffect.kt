package ui.feature.lobby

sealed class LobbyScreenUiEffect {
    data class ShowSnackbar(val message: String) : LobbyScreenUiEffect()
}
