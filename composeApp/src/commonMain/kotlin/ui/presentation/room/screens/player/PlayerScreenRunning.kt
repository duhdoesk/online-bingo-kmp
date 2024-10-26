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
import ui.presentation.common.components.SingleButtonRow
import ui.presentation.room.event.RoomPlayerEvent
import ui.presentation.room.screens.component.BingoButton
import ui.presentation.room.screens.component.CompactSelectedBingoCard
import ui.presentation.room.screens.component.RaffledAmount
import ui.presentation.room.screens.component.RaffledPresentation
import ui.presentation.room.screens.component.SpectatorModeInfo
import ui.presentation.room.state.RoomPlayerState
import ui.presentation.room.state.auxiliar.CardState

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

        BingoButton(
            enabled = screenState.canCallBingo,
            onClick = { event(RoomPlayerEvent.CallBingo) },
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
        )

        SingleButtonRow(
            onClick = { event(RoomPlayerEvent.PopBack) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}