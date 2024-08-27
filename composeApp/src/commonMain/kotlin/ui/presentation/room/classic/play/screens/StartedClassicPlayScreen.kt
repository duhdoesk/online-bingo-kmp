package ui.presentation.room.classic.play.screens

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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.call_bingo_button
import themedbingo.composeapp.generated.resources.new_card_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.classic.host.screens.component.ClassicRunningRoomScreenComposable
import ui.presentation.room.classic.play.event.ClassicPlayScreenUIEvent
import ui.presentation.room.classic.play.screens.component.CompactClassicCard
import ui.presentation.room.classic.play.state.ClassicPlayScreenUIState
import ui.presentation.room.common.PlayersLazyRow

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StartedClassicPlayScreen(
    uiState: ClassicPlayScreenUIState,
    uiEvent: (event: ClassicPlayScreenUIEvent) -> Unit,
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
                    maxWinners = uiState.maxWinners,
                    modifier = Modifier.fillMaxWidth(),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    ClassicRunningRoomScreenComposable(
                        raffledNumbers = uiState.raffledNumbers,
                        totalNumbers = uiState.numbers.size,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    if (uiState.myCard.isNotEmpty()) {
                        Spacer(Modifier.height(48.dp))

                        CompactClassicCard(
                            numbers = uiState.myCard,
                            raffledNumbers = uiState.raffledNumbers,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                        )
                    }
                }

                BottomButtonRow(
                    leftEnabled = true,
                    rightEnabled = uiState.canCallBingo,
                    leftClicked = { uiEvent(ClassicPlayScreenUIEvent.PopBack) },
                    rightClicked = { uiEvent(ClassicPlayScreenUIEvent.CallBingo) },
                    rightText = Res.string.call_bingo_button,
                    rightButtonHasIcon = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}