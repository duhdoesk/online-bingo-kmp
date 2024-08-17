package ui.presentation.room.classic.host.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.room.model.RoomState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button
import themedbingo.composeapp.generated.resources.finish_button
import themedbingo.composeapp.generated.resources.raffle_button
import themedbingo.composeapp.generated.resources.raffled
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.classic.host.NUMBERS
import ui.presentation.room.classic.host.event.ClassicHostScreenUIEvent
import ui.presentation.room.classic.host.screens.component.ClassicRunningRoomScreenComposable
import ui.presentation.room.classic.host.screens.component.RaffledNumbersLazyRow
import ui.presentation.room.classic.host.state.ClassicHostScreenUIState
import ui.presentation.room.common.FinishedRoomScreenComposable
import ui.presentation.room.common.PlayersLazyRow
import ui.presentation.room.themed.host.event.HostScreenUIEvent
import ui.presentation.room.themed.play.event.PlayScreenUIEvent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StartedClassicHostScreen(
    uiState: ClassicHostScreenUIState,
    uiEvent: (event: ClassicHostScreenUIEvent) -> Unit,
) {
    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                PlayersLazyRow(
                    players = uiState.players,
                    winners = uiState.winners,
                    maxWinners = uiState.maxWinners,
                    modifier = Modifier.fillMaxWidth(),
                )

                when (uiState.bingoState) {
                    RoomState.RUNNING -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            ClassicRunningRoomScreenComposable(
                                raffledNumbers = uiState.raffledNumbers,
                                totalNumbers = uiState.numbers.size,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = { uiEvent(ClassicHostScreenUIEvent.RaffleNextNumber) },
                                modifier = Modifier.width(200.dp),
                                enabled = uiState.canRaffleNextNumber,
                            ) {
                                Text(stringResource(Res.string.raffle_button))
                            }

                            Spacer(Modifier.height(48.dp))

                            Text(
                                text = stringResource(Res.string.raffled),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                            )

                            Spacer(Modifier.height(8.dp))

                            RaffledNumbersLazyRow(
                                raffledNumbers = uiState.raffledNumbers.reversed(),
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        BottomButtonRow(
                            leftEnabled = true,
                            rightEnabled = true,
                            leftClicked = { uiEvent(ClassicHostScreenUIEvent.PopBack) },
                            rightClicked = { uiEvent(ClassicHostScreenUIEvent.FinishRaffle) },
                            rightText = Res.string.finish_button,
                            rightButtonColors = ButtonDefaults.buttonColors().copy(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError,
                            ),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        )
                    }
                    else -> {
                        FinishedRoomScreenComposable(
                            winners = uiState.winners,
                            maxWinners = uiState.maxWinners,
                            modifier = Modifier
                                .weight(1f),
                        )

                        TextButton(
                            onClick = { uiEvent(ClassicHostScreenUIEvent.PopBack) },
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
}