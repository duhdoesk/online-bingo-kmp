package ui.presentation.room.screens.host

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.pop_back_dialog_body
import themedbingo.composeapp.generated.resources.pop_back_dialog_title
import themedbingo.composeapp.generated.resources.start_button
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.room.event.RoomHostEvent
import ui.presentation.room.screens.component.RoomInfo
import ui.presentation.room.state.RoomHostState
import ui.presentation.util.dialog.GenericActionDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HostScreenNotStarted(
    screenState: RoomHostState,
    event: (event: RoomHostEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showPopBackConfirmation by remember { mutableStateOf(false) }

    if (showPopBackConfirmation) {
        GenericActionDialog(
            onDismiss = { showPopBackConfirmation = false },
            onConfirm = {
                showPopBackConfirmation = false
                event(RoomHostEvent.PopBack)
            },
            title = Res.string.pop_back_dialog_title,
            body = Res.string.pop_back_dialog_body
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        RoomInfo(
            roomName = screenState.roomName,
            maxWinners = screenState.maxWinners,
            bingoStyle = screenState.bingoStyle,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
    }

    DoubleButtonRow(
        leftEnabled = true,
        rightEnabled = true,
        leftClicked = { showPopBackConfirmation = true },
        rightClicked = { event(RoomHostEvent.StartRaffle) },
        rightText = Res.string.start_button,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    )
}
