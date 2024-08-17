package ui.presentation.room.classic.play.event

sealed class ClassicPlayScreenUIEvent {
    data object UiLoaded: ClassicPlayScreenUIEvent()
    data object PopBack: ClassicPlayScreenUIEvent()
    data object ConfirmPopBack: ClassicPlayScreenUIEvent()
    data object GetNewCard: ClassicPlayScreenUIEvent()
    data object CallBingo: ClassicPlayScreenUIEvent()
}