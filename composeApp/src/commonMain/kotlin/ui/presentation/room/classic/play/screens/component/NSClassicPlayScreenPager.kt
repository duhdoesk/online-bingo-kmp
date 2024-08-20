package ui.presentation.room.classic.play.screens.component

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
import domain.room.model.BingoType
import ui.presentation.room.classic.play.state.ClassicPlayScreenUIState
import ui.presentation.room.common.RoomInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NSClassicPlayScreenPager(
    uiState: ClassicPlayScreenUIState,
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
                ClassicCardSelector(
                    card = uiState.myCard,
                    bingoType = BingoType.CLASSIC,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                )
            }

            else -> RoomInfo(
                roomName = uiState.roomName,
                theme = null,
                maxWinners = uiState.maxWinners,
                bingoType = BingoType.CLASSIC,
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = 400.dp),
            )
        }
    }
}