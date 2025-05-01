package ui.presentation.joinRoom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.core.dialog.GenericErrorDialog
import ui.presentation.joinRoom.event.JoinRoomUIEvent
import ui.presentation.joinRoom.screens.PortraitJoinScreen
import ui.presentation.joinRoom.screens.component.JoinRoomBottomSheet
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun JoinScreen(
    component: JoinScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * Component (ViewModel) trigger
     */
    LaunchedEffect(Unit) { component.uiEvent(JoinRoomUIEvent.UiLoaded) }

    /**
     * UI State pointers
     */
    val uiState by component.uiState.collectAsState()
    val themes by component.themes.collectAsState(emptyList())

    /**
     * Modal visibility holders
     */
    val roomDialogState = component.tapRoomDialogState
    val errorDialogState = component.errorDialogState

    /**
     * Screen
     */
    PortraitJoinScreen(
        uiState = uiState,
        uiEvent = { component.uiEvent(it) },
        themes = themes
    )

    /**
     * Join Room Bottom Sheet
     */
    if (roomDialogState.isVisible.value) {
        JoinRoomBottomSheet(
            room = roomDialogState.dialogData.value!!,
            onDismiss = { roomDialogState.hideDialog() },
            onConfirm = { password ->
                roomDialogState.hideDialog()
                component.uiEvent(
                    JoinRoomUIEvent.JoinRoom(
                        roomId = roomDialogState.dialogData.value!!.id,
                        roomPassword = password
                    )
                )
            }
        )
    }

    /**
     * Error Dialog
     */
    if (errorDialogState.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { errorDialogState.hideDialog() },
            body = errorDialogState.dialogData.value
        )
    }
}
