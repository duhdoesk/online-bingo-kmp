package ui.presentation.create_room.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.theme.model.BingoTheme
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.common.RotateScreen
import ui.presentation.util.WindowInfo

@Composable
fun LandscapeCreateRoomScreen(
    themes: List<BingoTheme>,
    uiState: CreateScreenUiState,
    isFormOk: Boolean,
    windowInfo: WindowInfo,
    event: (event: CreateScreenEvent) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (windowInfo.screenHeightInfo) {
            is WindowInfo.WindowType.Compact -> {
                RotateScreen()
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 800.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PortraitCreateRoomScreen(
                        themes = themes,
                        uiState = uiState,
                        isFormOk = isFormOk
                    ) { receivedEvent ->
                        event(receivedEvent)
                    }
                }
            }
        }
    }
}