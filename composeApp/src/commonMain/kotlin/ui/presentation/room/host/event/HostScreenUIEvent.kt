package ui.presentation.room.host.event

sealed class HostScreenUIEvent {
    data object UiLoaded: HostScreenUIEvent()
    data object PopBack: HostScreenUIEvent()
    data object ConfirmPopBack: HostScreenUIEvent()
    data object StartRaffle: HostScreenUIEvent()
    data object FinishRaffle: HostScreenUIEvent()
    data object ConfirmFinishRaffle: HostScreenUIEvent()
    data object RaffleNextCharacter: HostScreenUIEvent()
}