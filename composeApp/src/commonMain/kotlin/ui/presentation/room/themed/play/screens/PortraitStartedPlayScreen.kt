package ui.presentation.room.themed.play.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import themedbingo.composeapp.generated.resources.call_bingo_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.common.FinishedRoomScreenComposable
import ui.presentation.room.common.PlayersLazyRow
import ui.presentation.room.common.SpectatorModeInfo
import ui.presentation.room.themed.component.RunningRoomScreenComposable
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.room.themed.play.screens.component.SelectedBingoCard
import ui.presentation.room.themed.play.state.CardState
import ui.presentation.room.themed.play.state.PlayScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitStartedPlayScreen(
    uiState: PlayScreenUIState,
    uiEvent: (uiEvent: PlayScreenUIEvent) -> Unit
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

                        Spacer(Modifier.height(16.dp))

                        if (uiState.myCard is CardState.Success) {
                            SelectedBingoCard(
                                bingoCard = uiState.myCard.characters,
                                raffledCharacters = uiState.raffledCharacters,
                                modifier = Modifier
                                    .padding(16.dp).fillMaxWidth(),
                            )
                        } else {
                            SpectatorModeInfo(
                                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                            )
                        }
                    }

                    BottomButtonRow(
                        leftEnabled = true,
                        rightEnabled = uiState.canCallBingo,
                        leftClicked = { uiEvent(PlayScreenUIEvent.PopBack) },
                        rightClicked = { uiEvent(PlayScreenUIEvent.CallBingo) },
                        rightText = Res.string.call_bingo_button,
                        rightButtonHasIcon = false,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )

                } else {
                    FinishedRoomScreenComposable(
                        winners = uiState.winners,
                        maxWinners = uiState.maxWinners,
                        modifier = Modifier
                            .weight(1f),
                    )

                    TextButton(
                        onClick = { uiEvent(PlayScreenUIEvent.PopBack) },
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