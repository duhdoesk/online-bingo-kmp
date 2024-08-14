package ui.presentation.room.host.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.BingoType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_type_card
import themedbingo.composeapp.generated.resources.classic_card
import themedbingo.composeapp.generated.resources.max_winners_card
import themedbingo.composeapp.generated.resources.players_card
import themedbingo.composeapp.generated.resources.room_name_card
import themedbingo.composeapp.generated.resources.start_button
import themedbingo.composeapp.generated.resources.theme_card
import themedbingo.composeapp.generated.resources.themed_card
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.room.host.event.HostScreenUIEvent
import ui.presentation.room.component.PlayersLazyRow
import ui.presentation.room.component.RoomInfoCard
import ui.presentation.room.host.state.HostScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitNotStartedHostScreen(
    uiState: HostScreenUIState,
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
                    modifier = Modifier.fillMaxWidth(),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        alignment = Alignment.CenterVertically,
                        space = 16.dp,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    val cardModifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()

                    CreateRoomHeader(Modifier.fillMaxWidth())

                    Spacer(Modifier.height(16.dp))

                    RoomInfoCard(
                        key = Res.string.room_name_card,
                        value = uiState.roomName,
                        modifier = cardModifier
                    )

                    val bingoType = when (uiState.bingoType) {
                        BingoType.CLASSIC -> Res.string.classic_card
                        BingoType.THEMED -> Res.string.themed_card
                    }

                    RoomInfoCard(
                        key = Res.string.bingo_type_card,
                        value = stringResource(bingoType),
                        modifier = cardModifier
                    )

                    if (uiState.bingoType == BingoType.THEMED) {
                        RoomInfoCard(
                            key = Res.string.theme_card,
                            value = uiState.theme!!.name,
                            modifier = cardModifier
                        )
                    }

                    RoomInfoCard(
                        key = Res.string.players_card,
                        value = uiState.players.size.toString(),
                        modifier = cardModifier
                    )

                    RoomInfoCard(
                        key = Res.string.max_winners_card,
                        value = uiState.maxWinners.toString(),
                        modifier = cardModifier
                    )
                }

                BottomButtonRow(
                    leftEnabled = true,
                    rightEnabled = true,
                    leftClicked = { uiEvent(HostScreenUIEvent.PopBack) },
                    rightClicked = { uiEvent(HostScreenUIEvent.StartRaffle) },
                    rightText = Res.string.start_button,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}