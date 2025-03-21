package ui.presentation.room.event

sealed class RoomPlayerEvent {
    data object UiLoaded : RoomPlayerEvent()
    data object PopBack : RoomPlayerEvent()
    data object ConfirmPopBack : RoomPlayerEvent()
    data object GetNewCard : RoomPlayerEvent()
    data object CallBingo : RoomPlayerEvent()
}
