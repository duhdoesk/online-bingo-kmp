package ui.presentation.room.screens.host

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.finish_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.event.RoomHostEvent
import ui.presentation.room.screens.component.RaffleNextButton
import ui.presentation.room.screens.component.RaffledAmount
import ui.presentation.room.screens.component.RaffledPresentation
import ui.presentation.room.state.RoomHostState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HostScreenRunning(
    screenState: RoomHostState,
    event: (event: RoomHostEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        RaffledAmount(
            raffled = screenState.raffledItems.size,
            bingoStyle = screenState.bingoStyle,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        RaffledPresentation(
            bingoStyle = screenState.bingoStyle,
            raffled = screenState.raffledItems,
        )

        Spacer(Modifier.height(16.dp))

        RaffleNextButton(
            enabled = screenState.canRaffleNext,
            onClick = { event(RoomHostEvent.RaffleNextItem) }
        )
    }

    BottomButtonRow(
        leftEnabled = true,
        rightEnabled = true,
        leftClicked = { event(RoomHostEvent.PopBack) },
        rightClicked = { event(RoomHostEvent.FinishRaffle) },
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