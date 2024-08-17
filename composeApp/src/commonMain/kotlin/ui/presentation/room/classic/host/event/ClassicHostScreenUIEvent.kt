package ui.presentation.room.classic.host.event


sealed class ClassicHostScreenUIEvent {
    data object UiLoaded: ClassicHostScreenUIEvent()
    data object PopBack: ClassicHostScreenUIEvent()
    data object ConfirmPopBack: ClassicHostScreenUIEvent()
    data object StartRaffle: ClassicHostScreenUIEvent()
    data object FinishRaffle: ClassicHostScreenUIEvent()
    data object ConfirmFinishRaffle: ClassicHostScreenUIEvent()
    data object RaffleNextNumber: ClassicHostScreenUIEvent()
}