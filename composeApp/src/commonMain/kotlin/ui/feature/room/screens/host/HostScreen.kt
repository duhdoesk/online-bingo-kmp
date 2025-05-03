package ui.feature.room.screens.host

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.finish_raffle_error
import themedbingo.composeapp.generated.resources.start_raffle_error
import ui.feature.room.RoomHostViewModel
import ui.feature.room.event.RoomHostEvent
import ui.feature.room.screens.component.PlayersLazyRow
import ui.feature.room.state.HostScreenError
import ui.feature.room.state.auxiliar.BingoState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HostScreen(viewModel: RoomHostViewModel) {
    /**
     * Triggers viewModel's initial data loading function
     */
    LaunchedEffect(Unit) { viewModel.uiEvent(RoomHostEvent.UiLoaded) }

    /**
     * UI State listener
     */
    val screenState by viewModel.screenState.collectAsState()

    /**
     * Snackbar host state and string resources
     */
    val snackbarHostState = remember { SnackbarHostState() }
    val startRaffleErrorMessage = stringResource(Res.string.start_raffle_error)
    val finishRaffleErrorMessage = stringResource(Res.string.finish_raffle_error)

    /**
     * Triggers Snackbar when there is any error
     */
    LaunchedEffect(screenState.hostScreenError) {
        if (screenState.hostScreenError == null) return@LaunchedEffect

        val errorMessage =
            if (screenState.hostScreenError == HostScreenError.START) {
                startRaffleErrorMessage
            } else {
                finishRaffleErrorMessage
            }

        snackbarHostState.showSnackbar(
            message = errorMessage,
            actionLabel = null,
            withDismissAction = true,
            duration = SnackbarDuration.Short
        )

        viewModel.uiEvent(RoomHostEvent.CleanErrors)
    }

    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    /**
     * Screen calling
     */
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                    host = screenState.host,
                    winners = screenState.winners,
                    modifier = Modifier.fillMaxWidth()
                )

                when (screenState.bingoState) {
                    BingoState.NOT_STARTED -> HostScreenNotStarted(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) },
                        modifier = Modifier.weight(1f)
                    )

                    BingoState.RUNNING -> HostScreenRunning(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) },
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )

                    BingoState.FINISHED -> HostScreenFinished(
                        winners = screenState.winners,
                        maxWinners = screenState.maxWinners,
                        event = { viewModel.uiEvent(it) }
                    )
                }
            }
        }
    }
}
