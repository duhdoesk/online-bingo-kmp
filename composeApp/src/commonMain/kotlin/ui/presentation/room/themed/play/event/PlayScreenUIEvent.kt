package ui.presentation.room.themed.play.event

sealed class PlayScreenUIEvent {
    data object UiLoaded: PlayScreenUIEvent()
    data object PopBack: PlayScreenUIEvent()
    data object ConfirmPopBack: PlayScreenUIEvent()
    data object GetNewCard: PlayScreenUIEvent()
    data object CallBingo: PlayScreenUIEvent()
}