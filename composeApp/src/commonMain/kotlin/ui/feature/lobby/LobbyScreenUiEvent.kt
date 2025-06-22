package ui.feature.lobby

import domain.room.model.BingoRoom

sealed class LobbyScreenUiEvent {
    data class OnJoinRoom(val room: BingoRoom, val roomPassword: String?) : LobbyScreenUiEvent()
    data class OnQueryChange(val query: String) : LobbyScreenUiEvent()
    data object OnCreateRoom : LobbyScreenUiEvent()
    data object OnNavigateToPaywall : LobbyScreenUiEvent()
    data object OnPopBack : LobbyScreenUiEvent()
    data object OnRetry : LobbyScreenUiEvent()
}
