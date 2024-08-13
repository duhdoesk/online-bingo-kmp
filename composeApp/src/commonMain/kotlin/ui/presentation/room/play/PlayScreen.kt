package ui.presentation.room.play

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.ModifierLocal
import ui.presentation.room.play.event.PlayScreenUIEvent
import ui.presentation.util.WindowInfo

@Composable
fun PlayScreen(
    component: PlayScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(PlayScreenUIEvent.UiLoaded) }

    val uiState by component.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Text(uiState.roomName)
        Text(uiState.bingoState.name)
        Text(uiState.theme?.name ?: "")
        Text(uiState.winners.toString())
        Text(uiState.bingoType.name)
        Text(uiState.calledBingo.toString())
        Text(uiState.canCallBingo.toString())
        Text(uiState.loading.toString())
        Text(uiState.myCard.toString())

        Button(onClick = { component.uiEvent(PlayScreenUIEvent.GetNewCard) }) {
            Text("Get New Card")
        }
    }
}