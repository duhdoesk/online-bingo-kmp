package ui.presentation.join_room

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.common.RotateScreen
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.screens.PortraitJoinScreen
import ui.presentation.join_room.screens.component.JoinRoomBottomSheet
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun JoinScreen(
    component: JoinScreenComponent,
    windowInfo: WindowInfo
) {

    val uiState by component.uiState.collectAsState()
    val themes by component.themes.collectAsState(emptyList())
    val roomDialogState = component.tapRoomDialogState
    val errorDialogState = component.errorDialogState

    LaunchedEffect(Unit) { component.uiEvent(JoinRoomUIEvent.UiLoaded) }

    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitJoinScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) },
                themes = themes,
            )
    }

    if (roomDialogState.isVisible.value) {
        JoinRoomBottomSheet(
            room = roomDialogState.dialogData.value!!,
            onDismiss = { roomDialogState.hideDialog() },
            onConfirm = { password ->
                roomDialogState.hideDialog()
                component.uiEvent(
                    JoinRoomUIEvent.JoinRoom(
                        roomId = roomDialogState.dialogData.value!!.id,
                        roomPassword = password,
                    )
                )
            },
        )
    }

    if (errorDialogState.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { errorDialogState.hideDialog() },
            body = errorDialogState.dialogData.value
        )
    }
}