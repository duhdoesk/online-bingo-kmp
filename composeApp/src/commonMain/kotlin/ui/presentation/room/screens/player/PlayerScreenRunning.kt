package ui.presentation.room.screens.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.call_bingo_button
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.room.event.RoomPlayerEvent
import ui.presentation.room.screens.component.CompactSelectedBingoCard
import ui.presentation.room.screens.component.RaffledAmount
import ui.presentation.room.screens.component.RaffledPresentation
import ui.presentation.room.screens.component.SpectatorModeInfo
import ui.presentation.room.state.RoomPlayerState
import ui.presentation.room.state.auxiliar.CardState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayerScreenRunning(
    screenState: RoomPlayerState,
    event: (uiEvent: RoomPlayerEvent) -> Unit,
) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            RaffledAmount(
                raffled = screenState.raffledItems.size,
                bingoStyle = screenState.bingoStyle,
            )

            RaffledPresentation(
                bingoStyle = screenState.bingoStyle,
                raffled = screenState.raffledItems,
            )

            Spacer(Modifier.height(16.dp))

            if (screenState.cardState is CardState.Success) {
                CompactSelectedBingoCard(
                    bingoStyle = screenState.bingoStyle,
                    cardState = screenState.cardState,
                    raffled = screenState.raffledItems,
                )
            } else {
                SpectatorModeInfo(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                )
            }
        }

        DoubleButtonRow(
            leftEnabled = true,
            rightEnabled = screenState.canCallBingo,
            leftClicked = { event(RoomPlayerEvent.PopBack) },
            rightClicked = { event(RoomPlayerEvent.CallBingo) },
            rightText = Res.string.call_bingo_button,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}