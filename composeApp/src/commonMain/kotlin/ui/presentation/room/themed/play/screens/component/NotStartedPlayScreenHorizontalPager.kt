package ui.presentation.room.themed.play.screens.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.presentation.room.themed.play.state.PlayScreenUIState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotStartedPlayScreenHorizontalPager(
    uiState: PlayScreenUIState,
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
            0 -> if (uiState.myCard.isNotEmpty()) {
                CardSelector(
                    card = uiState.myCard,
                    bingoType = uiState.bingoType,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

            else -> RoomInfo(
                uiState = uiState,
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 400.dp),
            )
        }
    }
}