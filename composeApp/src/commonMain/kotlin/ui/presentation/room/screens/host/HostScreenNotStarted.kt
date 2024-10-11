package ui.presentation.room.screens.host

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.start_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.event.RoomHostEvent
import ui.presentation.room.screens.component.RoomInfo
import ui.presentation.room.state.RoomHostState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HostScreenNotStarted(
    screenState: RoomHostState,
    event: (event: RoomHostEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        RoomInfo(
            roomName = screenState.roomName,
            maxWinners = screenState.maxWinners,
            bingoStyle = screenState.bingoStyle,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
    }

    BottomButtonRow(
        leftEnabled = true,
        rightEnabled = true,
        leftClicked = { event(RoomHostEvent.PopBack) },
        rightClicked = { event(RoomHostEvent.StartRaffle) },
        rightText = Res.string.start_button,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    )
}