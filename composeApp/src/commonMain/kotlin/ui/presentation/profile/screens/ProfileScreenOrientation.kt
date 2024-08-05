package ui.presentation.profile.screens

import androidx.compose.runtime.Composable
import domain.user.model.User
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.WindowInfo

@Composable
fun ProfileScreenOrientation(
    windowInfo: WindowInfo,
    uiState: ProfileScreenUIState.Success,
    event: (event: ProfileScreenEvent) -> Unit,
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeProfileScreen(
                windowInfo = windowInfo,
                uiState = uiState,
                event = { landscapeEvent -> event(landscapeEvent) },
            )

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitProfileScreen(
                windowInfo = windowInfo,
                uiState = uiState,
                event = { landscapeEvent -> event(landscapeEvent) },
            )
    }

}