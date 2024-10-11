package ui.presentation.room.screens.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.presentation.room.state.RoomPlayerState
import ui.presentation.room.state.auxiliar.CardState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotStartedPlayScreenHorizontalPager(
    screenState: RoomPlayerState,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSpacing = 8.dp,
        beyondBoundsPageCount = 2,
        verticalAlignment = Alignment.Top,
    ) { index ->
        when (index) {
            0 -> when (screenState.cardState) {
                is CardState.Success -> {
                    CardSelector(
                        cardState = screenState.cardState,
                        bingoStyle = screenState.bingoStyle,
                    )
                }

                else -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            else -> RoomInfo(
                roomName = screenState.roomName,
                maxWinners = screenState.maxWinners,
                bingoStyle = screenState.bingoStyle,
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 400.dp),
            )
        }
    }
}