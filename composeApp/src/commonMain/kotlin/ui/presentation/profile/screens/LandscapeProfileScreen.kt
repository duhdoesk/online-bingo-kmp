package ui.presentation.profile.screens

import androidx.compose.runtime.Composable
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.common.RotateScreen
import ui.presentation.util.WindowInfo

@Composable
fun LandscapeProfileScreen(
    windowInfo: WindowInfo,
    uiState: ProfileScreenUIState,
    event: (event: ProfileScreenEvent) -> Unit,
) {
    RotateScreen()
}