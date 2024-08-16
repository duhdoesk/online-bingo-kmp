package ui.presentation.room.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import ui.presentation.common.RotateScreen
import ui.presentation.room.play.screens.PortraitPlayScreen
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericActionDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayScreen(
    component: PlayScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(PlayScreenUIEvent.UiLoaded) }

    val uiState by component.uiState.collectAsState()
    val popBackDialogState = component.popBackDialogState

    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitPlayScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )
    }

    if (popBackDialogState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { popBackDialogState.hideDialog() },
            onConfirm = {
                popBackDialogState.hideDialog()
                component.uiEvent(PlayScreenUIEvent.ConfirmPopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body,
            permanentAction = false,
        )
    }
}