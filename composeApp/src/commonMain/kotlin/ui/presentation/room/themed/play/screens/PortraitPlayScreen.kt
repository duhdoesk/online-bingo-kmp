package ui.presentation.room.themed.play.screens

import androidx.compose.runtime.Composable
import domain.room.model.RoomState
import ui.presentation.common.LoadingScreen
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.room.themed.play.state.PlayScreenUIState

@Composable
fun PortraitPlayScreen(
    uiState: PlayScreenUIState,
    uiEvent: (event: PlayScreenUIEvent) -> Unit,
) {
    if (uiState.loading) {
        LoadingScreen()
        return
    }

    when (uiState.bingoState) {
        RoomState.NOT_STARTED ->
            PortraitNotStartedPlayScreen(uiState = uiState, uiEvent = { uiEvent(it) })

        else ->
            PortraitStartedPlayScreen(uiState = uiState, uiEvent = { uiEvent(it) })
    }
}