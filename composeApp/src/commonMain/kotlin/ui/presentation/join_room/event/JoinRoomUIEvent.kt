package ui.presentation.join_room.event

sealed class JoinRoomUIEvent {
    data object PopBack: JoinRoomUIEvent()
    data object CreateRoom: JoinRoomUIEvent()
    data class JoinRoom(val roomId: String): JoinRoomUIEvent()
}