package ui.presentation.createRoom.event

import domain.theme.model.BingoTheme

sealed class CreateScreenEvent {
    data object UILoaded : CreateScreenEvent()
    data object PopBack : CreateScreenEvent()
    data object CreateRoom : CreateScreenEvent()
    data object UpdateLocked : CreateScreenEvent()
    data class UpdateName(val name: String) : CreateScreenEvent()
    data class UpdatePassword(val password: String) : CreateScreenEvent()
    data class UpdateMaxWinners(val maxWinners: Int) : CreateScreenEvent()
    data class UpdateTheme(val theme: BingoTheme) : CreateScreenEvent()
}
