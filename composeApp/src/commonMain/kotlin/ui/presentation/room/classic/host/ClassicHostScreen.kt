package ui.presentation.room.classic.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import domain.room.model.RoomState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.finish_dialog_body
import themedbingo.composeapp.generated.resources.finish_dialog_title
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import ui.presentation.room.classic.host.event.ClassicHostScreenUIEvent
import ui.presentation.room.classic.host.screens.NotStartedClassicHostScreen
import ui.presentation.room.classic.host.screens.StartedClassicHostScreen
import ui.presentation.util.dialog.GenericActionDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClassicHostScreen(
    component: ClassicHostScreenComponent,
) {
    LaunchedEffect(Unit) { component.uiEvent(ClassicHostScreenUIEvent.UiLoaded) }

    val uiState by component.uiState.collectAsState()
    val popBackDialogState = component.popBackDialogState
    val finishRaffleDialogState = component.finishRaffleDialogState

    when (uiState.bingoState) {
        RoomState.NOT_STARTED -> {
            NotStartedClassicHostScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )
        }

        else -> {
            StartedClassicHostScreen(
                uiState = uiState,
                uiEvent = { component.uiEvent(it) }
            )
        }
    }

    if (finishRaffleDialogState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { finishRaffleDialogState.hideDialog() },
            onConfirm = {
                finishRaffleDialogState.hideDialog()
                component.uiEvent(ClassicHostScreenUIEvent.ConfirmFinishRaffle)
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
                component.uiEvent(ClassicHostScreenUIEvent.ConfirmPopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body,
            permanentAction = false,
        )
    }
}