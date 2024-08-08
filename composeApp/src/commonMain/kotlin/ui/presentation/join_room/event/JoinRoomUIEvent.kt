package ui.presentation.join_room.event

sealed class JoinRoomUIEvent {
    data object CreateRoom: JoinRoomUIEvent()
    data object PopBack: JoinRoomUIEvent()
    data object UiLoaded: JoinRoomUIEvent()
    data class JoinRoom(val roomId: String, val roomPassword: String?): JoinRoomUIEvent()
    data class TapRoom(val roomName: String): JoinRoomUIEvent()
}