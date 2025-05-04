package ui.feature.room.screens.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.new_card_button
import ui.feature.core.DoubleButtonRow
import ui.feature.room.event.RoomPlayerEvent
import ui.feature.room.screens.component.HorizontalPagerIndicator
import ui.feature.room.screens.component.NotStartedPlayScreenHorizontalPager
import ui.feature.room.state.RoomPlayerState

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerScreenNotStarted(
    screenState: RoomPlayerState,
    event: (uiEvent: RoomPlayerEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }

    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            NotStartedPlayScreenHorizontalPager(
                screenState = screenState,
                pagerState = pagerState,
                modifier = Modifier.fillMaxWidth(),
                onGetCard = { event(RoomPlayerEvent.GetNewCard) }
            )

            Spacer(Modifier.weight(1f))

            HorizontalPagerIndicator(
                pageCount = 2,
                currentPage = pagerState.currentPage,
                targetPage = pagerState.targetPage,
                currentPageOffsetFraction = pagerState.currentPageOffsetFraction
            )
        }

        DoubleButtonRow(
            leftEnabled = true,
            rightEnabled = true,
            leftClicked = { event(RoomPlayerEvent.PopBack) },
            rightClicked = {
                event(RoomPlayerEvent.GetNewCard)
                coroutineScope.launch { pagerState.animateScrollToPage(0) }
            },
            rightText = Res.string.new_card_button,
            rightButtonIcon = Icons.Default.Refresh,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}
