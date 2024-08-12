package ui.presentation.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.presentation.common.RotateScreen
import ui.presentation.host.event.HostScreenUIEvent
import ui.presentation.host.screens.PortraitHostScreen
import ui.presentation.util.WindowInfo

@Composable
fun HostScreen(
    component: HostScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(HostScreenUIEvent.UiLoaded) }

    val uiState by component.uiState.collectAsState()

    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitHostScreen(
                uiState = uiState,
                uiEvent = { event -> component.uiEvent(event) }
            )
    }
}