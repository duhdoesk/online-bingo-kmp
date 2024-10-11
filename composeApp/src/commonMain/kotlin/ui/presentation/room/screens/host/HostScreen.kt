package ui.presentation.room.screens.host

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.presentation.room.RoomHostViewModel
import ui.presentation.room.event.RoomHostEvent
import ui.presentation.room.screens.component.PlayersLazyRow
import ui.presentation.room.state.auxiliar.BingoState

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
     * Screen calling
     */
    Scaffold(
        modifier = Modifier.imePadding(),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                PlayersLazyRow(
                    players = screenState.players.reversed(),
                    winners = screenState.winners,
                    modifier = Modifier.fillMaxWidth(),
                )

                when (screenState.bingoState) {
                    BingoState.NOT_STARTED -> HostScreenNotStarted(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) },
                        modifier = Modifier.weight(1f),
                    )

                    BingoState.RUNNING -> HostScreenRunning(
                        screenState = screenState,
                        event = { viewModel.uiEvent(it) },
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                    )

                    BingoState.FINISHED -> HostScreenFinished(
                        winners = screenState.winners,
                        maxWinners = screenState.maxWinners,
                        event = { viewModel.uiEvent(it) },
                    )
                }
            }
        }
    }
}