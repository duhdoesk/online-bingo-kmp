package ui.presentation.room.play

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ui.presentation.room.play.event.PlayScreenUIEvent
import ui.presentation.util.WindowInfo

@Composable
fun PlayScreen(
    component: PlayScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(PlayScreenUIEvent.UiLoaded) }
}