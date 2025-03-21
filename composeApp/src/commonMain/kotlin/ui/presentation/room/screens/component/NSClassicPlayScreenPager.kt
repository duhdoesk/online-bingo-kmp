package ui.presentation.room.screens.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.presentation.room.state.RoomPlayerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NSClassicPlayScreenPager(
    screenState: RoomPlayerState,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
//    HorizontalPager(
//        state = pagerState,
//        modifier = modifier,
//        pageSpacing = 8.dp,
//        beyondBoundsPageCount = 2,
//        verticalAlignment = Alignment.Top,
//    ) { index ->
//        when (index) {
//            0 -> if (screenState.myCard.isNotEmpty()) {
//                BingoCardClassic(
//                    card = screenState.myCard,
//                    bingoType = BingoType.CLASSIC,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                )
//            }
//
//            else -> RoomInfo(
//                roomName = screenState.roomName,
//                theme = null,
//                maxWinners = screenState.maxWinners,
//                bingoType = BingoType.CLASSIC,
//                modifier = Modifier
//                    .padding(16.dp)
//                    .widthIn(max = 400.dp),
//            )
//        }
//    }
}
