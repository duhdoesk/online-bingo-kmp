package ui.presentation.room.classic.play.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.RoomState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.new_card_button
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.room.classic.play.event.ClassicPlayScreenUIEvent
import ui.presentation.room.classic.play.screens.component.NSClassicPlayScreenPager
import ui.presentation.room.classic.play.state.ClassicPlayScreenUIState
import ui.presentation.room.common.PlayersLazyRow
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.room.themed.play.screens.component.HorizontalPagerIndicator

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotStartedClassicPlayScreen(
    uiState: ClassicPlayScreenUIState,
    uiEvent: (event: ClassicPlayScreenUIEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }

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
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    NSClassicPlayScreenPager(
                        uiState = uiState,
                        pagerState = pagerState,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    )

                    Spacer(Modifier.weight(1f))

                    HorizontalPagerIndicator(
                        pageCount = 2,
                        currentPage = pagerState.currentPage,
                        targetPage = pagerState.targetPage,
                        currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                    )
                }

                BottomButtonRow(
                    leftEnabled = true,
                    rightEnabled = true,
                    leftClicked = { uiEvent(ClassicPlayScreenUIEvent.PopBack) },
                    rightClicked = {
                        uiEvent(ClassicPlayScreenUIEvent.GetNewCard)
                        coroutineScope.launch { pagerState.animateScrollToPage(0) }
                    },
                    rightText = Res.string.new_card_button,
                    rightButtonHasIcon = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}