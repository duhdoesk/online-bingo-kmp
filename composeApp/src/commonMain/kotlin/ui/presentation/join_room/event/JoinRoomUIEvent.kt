package ui.presentation.join_room.event

import domain.room.model.BingoRoom

sealed class JoinRoomUIEvent {
    data object CreateRoom: JoinRoomUIEvent()
    data object PopBack: JoinRoomUIEvent()
    data object UiLoaded: JoinRoomUIEvent()
    data class JoinRoom(val roomId: String, val roomPassword: String?): JoinRoomUIEvent()
    data class TapRoom(val room: BingoRoom): JoinRoomUIEvent()
    data class QueryTyping(val query: String): JoinRoomUIEvent()
}