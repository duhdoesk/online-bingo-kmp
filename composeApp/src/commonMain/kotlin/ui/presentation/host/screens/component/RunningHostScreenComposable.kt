package ui.presentation.host.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.raffle_button
import themedbingo.composeapp.generated.resources.raffled
import ui.presentation.host.event.HostScreenUIEvent
import ui.presentation.host.state.HostScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RunningHostScreenComposable(
    uiState: HostScreenUIState,
    uiEvent: (uiEvent: HostScreenUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = "${stringResource(Res.string.raffled)}: ${uiState.raffledCharacters.size} / ${uiState.themeCharacters.size}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        )

        RaffledCharactersHorizontalPager(
            characters = uiState.raffledCharacters,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { uiEvent(HostScreenUIEvent.RaffleNextCharacter) },
            modifier = Modifier.width(200.dp),
            enabled = uiState.canRaffleNextCharacter,
        ) {
            Text(stringResource(Res.string.raffle_button))
        }
    }
}