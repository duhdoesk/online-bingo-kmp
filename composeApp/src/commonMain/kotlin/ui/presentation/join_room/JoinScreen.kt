package ui.presentation.join_room

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.common.RotateScreen
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.screens.PortraitJoinScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun JoinScreen(
    component: JoinScreenComponent,
    windowInfo: WindowInfo
) {

    val uiState by component.uiState.collectAsState()
    val roomDialogState = component.tapRoomDialogState
    val errorDialogState = component.errorDialogState

    LaunchedEffect(Unit) { component.uiEvent(JoinRoomUIEvent.UiLoaded) }

    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitJoinScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )
    }

    if (roomDialogState.isVisible.value) {
        //todo(): show custom dialog - to be created still
    }

    if (errorDialogState.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { errorDialogState.hideDialog() },
            body = null
        )
    }
}