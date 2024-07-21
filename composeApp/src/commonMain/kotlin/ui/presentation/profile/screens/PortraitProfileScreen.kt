package ui.presentation.profile.screens

import androidx.compose.runtime.Composable
import domain.user.model.User
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.util.WindowInfo

@Composable
fun PortraitProfileScreen(
    windowInfo: WindowInfo,
    user: User?,
    event: (event: ProfileScreenEvent) -> Unit,
) {

}