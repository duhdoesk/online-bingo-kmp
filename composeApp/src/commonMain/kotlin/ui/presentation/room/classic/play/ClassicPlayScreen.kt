package ui.presentation.room.classic.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import domain.room.model.RoomState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import ui.presentation.room.classic.play.event.ClassicPlayScreenUIEvent
import ui.presentation.room.classic.play.screens.NotStartedClassicPlayScreen
import ui.presentation.room.classic.play.screens.StartedClassicPlayScreen
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClassicPlayScreen(
    component: ClassicPlayScreenComponent,
) {
    /**
     * Triggers viewModel's initial data loading function
     */
    LaunchedEffect(Unit) { component.uiEvent(ClassicPlayScreenUIEvent.UiLoaded) }

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
    when (uiState.bingoState) {
        RoomState.NOT_STARTED ->
            NotStartedClassicPlayScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )

        else ->
            StartedClassicPlayScreen(
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
                component.uiEvent(ClassicPlayScreenUIEvent.ConfirmPopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body,
            permanentAction = false,
        )
    }
}