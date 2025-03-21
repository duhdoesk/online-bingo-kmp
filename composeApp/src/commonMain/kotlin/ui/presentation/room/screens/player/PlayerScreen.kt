package ui.presentation.room.screens.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import ui.presentation.room.RoomPlayerViewModel
import ui.presentation.room.event.RoomPlayerEvent
import ui.presentation.room.screens.component.PlayersLazyRow
import ui.presentation.room.state.auxiliar.BingoState
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayerScreen(viewModel: RoomPlayerViewModel) {
    /**
     * Triggers viewModel's initial data loading function
     */
    LaunchedEffect(Unit) { viewModel.uiEvent(RoomPlayerEvent.UiLoaded) }

    /**
     * UI State listener
     */
    val screenState by viewModel.screenState.collectAsState()

    /**
     * Modal visibility holders
     */
    val showErrorMessage by viewModel.errorDialogState.isVisible
    val showPopBackConfirmation by viewModel.popBackDialogState.isVisible

    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    /**
     * Screen calling
     */
    Scaffold(
        modifier = Modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                PlayersLazyRow(
                    players = screenState.players.reversed(),
                    winners = screenState.winners,
                    host = screenState.host,
                    modifier = Modifier.fillMaxWidth()
                )

                when (screenState.bingoState) {
                    BingoState.FINISHED -> PlayerScreenFinished(
                        winners = screenState.winners,
                        maxWinners = screenState.maxWinners,
                        event = { viewModel.uiEvent(it) }
                    )

                    BingoState.NOT_STARTED -> PlayerScreenNotStarted(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) }
                    )

                    BingoState.RUNNING -> PlayerScreenRunning(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) }
                    )
                }
            }
        }
    }

    /**
     * Modals
     */
    if (showErrorMessage) {
        GenericErrorDialog(
            onDismiss = { viewModel.errorDialogState.hideDialog() },
            body = viewModel.errorDialogState.dialogData.value
        )
    }

    if (showPopBackConfirmation) {
        GenericActionDialog(
            onDismiss = { viewModel.popBackDialogState.hideDialog() },
            onConfirm = {
                viewModel.popBackDialogState.hideDialog()
                viewModel.uiEvent(RoomPlayerEvent.ConfirmPopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body,
            permanentAction = false
        )
    }
}
