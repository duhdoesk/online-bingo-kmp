package ui.presentation.room.host.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.RoomState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button
import themedbingo.composeapp.generated.resources.finish_button
import themedbingo.composeapp.generated.resources.raffle_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.host.event.HostScreenUIEvent
import ui.presentation.room.component.FinishedRoomScreenComposable
import ui.presentation.room.component.PlayersLazyRow
import ui.presentation.room.component.RunningRoomScreenComposable
import ui.presentation.room.host.state.HostScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitStartedHostScreen(
    uiState: HostScreenUIState,
    canRaffleNextCharacter: Boolean,
    uiEvent: (event: HostScreenUIEvent) -> Unit,
) {
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
                    players = uiState.players.reversed(),
                    winners = uiState.winners,
                    maxWinners = uiState.maxWinners,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (uiState.bingoState == RoomState.RUNNING) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        RunningRoomScreenComposable(
                            uiState = uiState,
                            modifier = Modifier,
                        )

                        Button(
                            onClick = { uiEvent(HostScreenUIEvent.RaffleNextCharacter) },
                            modifier = Modifier.width(200.dp),
                            enabled = canRaffleNextCharacter,
                        ) {
                            Text(stringResource(Res.string.raffle_button))
                        }
                    }


                    BottomButtonRow(
                        leftEnabled = true,
                        rightEnabled = true,
                        leftClicked = { uiEvent(HostScreenUIEvent.PopBack) },
                        rightClicked = { uiEvent(HostScreenUIEvent.FinishRaffle) },
                        rightText = Res.string.finish_button,
                        rightButtonColors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )
                } else {
                    FinishedRoomScreenComposable(
                        uiState = uiState,
                        modifier = Modifier
                            .weight(1f),
                    )

                    TextButton(
                        onClick = { uiEvent(HostScreenUIEvent.PopBack) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(Res.string.back_button)
                        )

                        Text(
                            text = stringResource(Res.string.back_button),
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }
                }
            }
        }
    }
}