package ui.feature.createRoom

import domain.room.model.RoomPrivacy

sealed class CreateRoomUiEvent {
    data class OnChangeMaxWinners(val maxWinners: Int) : CreateRoomUiEvent()
    data class OnChangeName(val name: String) : CreateRoomUiEvent()
    data class OnChangePrivacy(val privacy: RoomPrivacy) : CreateRoomUiEvent()
    data class OnChangeType(val type: CreateRoomUiState.Type) : CreateRoomUiEvent()
    data object OnCreateRoom : CreateRoomUiEvent()
    data object OnPopBack : CreateRoomUiEvent()
}
