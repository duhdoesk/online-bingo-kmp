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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClassicPlayScreen(
    component: ClassicPlayScreenComponent,
) {
    LaunchedEffect(Unit) { component.uiEvent(ClassicPlayScreenUIEvent.UiLoaded) }

    val uiState by component.uiState.collectAsState()
    val popBackDialogState = component.popBackDialogState

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