package ui.presentation.room.themed.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.finish_dialog_body
import themedbingo.composeapp.generated.resources.finish_dialog_title
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.presentation.common.RotateScreen
import ui.presentation.room.themed.host.event.HostScreenUIEvent
import ui.presentation.room.themed.host.screens.PortraitHostScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog
import ui.presentation.util.dialog.GenericSuccessDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HostScreen(
    component: HostScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * Launched Effect to trigger viewModel's data loading function
     */
    LaunchedEffect(Unit) { component.uiEvent(HostScreenUIEvent.UiLoaded) }

    /**
     * UI State listener
     */
    val uiState by component.uiState.collectAsState()

    /**
     * Modal visibility listeners
     */
    val finishRaffleDialogState = component.finishRaffleDialogState
    val popBackDialogState = component.popBackDialogState
    val showGenericErrorDialog = component.showGenericErrorDialog

    /**
     * Screen calling
     */
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitHostScreen(
                uiState = uiState,
                uiEvent = { event -> component.uiEvent(event) }
            )
    }

    /**
     * Modals
     */
    if (showGenericErrorDialog.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { showGenericErrorDialog.hideDialog() },
            body = Res.string.unmapped_error,
        )
    }

    if (finishRaffleDialogState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { finishRaffleDialogState.hideDialog() },
            onConfirm = {
                finishRaffleDialogState.hideDialog()
                component.uiEvent(HostScreenUIEvent.ConfirmFinishRaffle)
            },
            title = Res.string.finish_dialog_title,
            body = Res.string.finish_dialog_body,
            permanentAction = false,
        )
    }

    if (popBackDialogState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { popBackDialogState.hideDialog() },
            onConfirm = {
                popBackDialogState.hideDialog()
                component.uiEvent(HostScreenUIEvent.ConfirmPopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body,
            permanentAction = false,
        )
    }
}