package ui.feature.profile.screens

import androidx.compose.runtime.Composable
import ui.feature.core.RotateScreen
import ui.feature.profile.event.ProfileScreenEvent
import ui.feature.profile.state.ProfileScreenUIState
import ui.util.WindowInfo

@Composable
fun LandscapeProfileScreen(
    windowInfo: WindowInfo,
    uiState: ProfileScreenUIState,
    event: (event: ProfileScreenEvent) -> Unit
) {
    RotateScreen()
}
