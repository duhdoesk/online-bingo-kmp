package ui.presentation.host.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.user.model.User
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.finish_button
import themedbingo.composeapp.generated.resources.player_picture
import themedbingo.composeapp.generated.resources.raffle_button
import themedbingo.composeapp.generated.resources.raffled
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.host.event.HostScreenUIEvent
import ui.presentation.host.screens.component.PlayersLazyRow
import ui.presentation.host.screens.component.RaffledCharactersHorizontalPager
import ui.presentation.host.screens.component.WinnerCard
import ui.presentation.host.state.HostScreenUIState
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitRunningHostScreen(
    uiState: HostScreenUIState,
    uiEvent: (event: HostScreenUIEvent) -> Unit,
) {

    var displayWinnerCard by remember { mutableStateOf(false) }
    var surfaceColor = getRandomLightColor()

    LaunchedEffect(uiState.winners) {
        if (uiState.winners.size > 0) {
            surfaceColor = getRandomLightColor()
            displayWinnerCard = true
            delay(5000)
            displayWinnerCard = false
        }
    }

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

                AnimatedVisibility(displayWinnerCard) {
                    WinnerCard(
                        surfaceColor = surfaceColor,
                        winner = uiState.winners.last(),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
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

                BottomButtonRow(
                    leftEnabled = true,
                    rightEnabled = true,
                    leftClicked = { uiEvent(HostScreenUIEvent.PopBack) },
                    rightClicked = { uiEvent(HostScreenUIEvent.FinishRaffle) },
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
        }
    }
}