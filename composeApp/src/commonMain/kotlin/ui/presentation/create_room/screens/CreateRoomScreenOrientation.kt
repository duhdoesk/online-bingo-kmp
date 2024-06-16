package ui.presentation.create_room.screens

import androidx.compose.runtime.Composable
import domain.theme.model.BingoTheme
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.util.WindowInfo

@Composable
fun CreateRoomScreenOrientation(
    windowInfo: WindowInfo,
    themes: List<BingoTheme>,
    uiState: CreateScreenUiState,
    isFormOk: Boolean,
    event: (event: CreateScreenEvent) -> Unit
) {

    when (windowInfo.screenOrientation) {
        is WindowInfo.DeviceOrientation.Landscape ->
            LandscapeCreateRoomScreen(
                themes = themes,
                uiState = uiState,
                isFormOk = isFormOk,
                windowInfo = windowInfo
            ) { landscapeEvent ->
                event(landscapeEvent)
            }

        else ->
            PortraitCreateRoomScreen(
                themes = themes,
                uiState = uiState,
                isFormOk = isFormOk
            ) { portraitEvent ->
                event(portraitEvent)
            }
    }
}