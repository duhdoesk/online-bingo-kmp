package ui.presentation.room.host.screens

import androidx.compose.runtime.Composable
import domain.room.model.RoomState
import ui.presentation.common.LoadingScreen
import ui.presentation.room.host.event.HostScreenUIEvent
import ui.presentation.room.host.state.HostScreenUIState

@Composable
fun PortraitHostScreen(
    uiState: HostScreenUIState,
    canRaffleNextCharacter: Boolean,
    uiEvent: (event: HostScreenUIEvent) -> Unit,
) {
    if (uiState.loading) {
        LoadingScreen()
        return
    }

    when (uiState.bingoState) {
        RoomState.NOT_STARTED ->
            PortraitNotStartedHostScreen(uiState = uiState, uiEvent = { uiEvent(it) })

        else ->
            PortraitStartedHostScreen(
                uiState = uiState,
                canRaffleNextCharacter = canRaffleNextCharacter,
                uiEvent = { uiEvent(it) })
    }
}