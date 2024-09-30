package ui.presentation.room.themed.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import ui.presentation.common.RotateScreen
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.room.themed.play.screens.PortraitPlayScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayScreen(
    component: PlayScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * Triggers viewModel's initial data loading function
     */
    LaunchedEffect(Unit) { component.uiEvent(PlayScreenUIEvent.UiLoaded) }

    /**
     * UI State listener
     */
    val uiState by component.uiState.collectAsState()

    /**
     * Modal visibility listeners
     */
    val popBackDialogState = component.popBackDialogState
    val showErrorDialog = component.showErrorDialog

    /**
     * Screen calling
     */
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitPlayScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )
    }

    /**
     * Modals
     */
    if (showErrorDialog.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { showErrorDialog.hideDialog() },
            body = showErrorDialog.dialogData.value,
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