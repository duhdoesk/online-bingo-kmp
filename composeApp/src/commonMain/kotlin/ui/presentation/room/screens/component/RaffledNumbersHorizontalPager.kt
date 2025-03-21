package ui.presentation.room.screens.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.blue_ball
import themedbingo.composeapp.generated.resources.go_to_first
import themedbingo.composeapp.generated.resources.green_ball
import themedbingo.composeapp.generated.resources.purple_ball
import themedbingo.composeapp.generated.resources.red_ball
import themedbingo.composeapp.generated.resources.yellow_ball

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun RaffledNumbersHorizontalPager(
    raffledNumbers: List<Int>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { raffledNumbers.size }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 100.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) { index ->
            val pageOffset =
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset < -0.1f || pageOffset > 0.1f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
            ) {
                val painter = when (raffledNumbers[index]) {
                    in 1..14 -> Res.drawable.blue_ball
                    in 15..29 -> Res.drawable.purple_ball
                    in 30..44 -> Res.drawable.yellow_ball
                    in 45..59 -> Res.drawable.green_ball
                    else -> Res.drawable.red_ball
                }

                Image(
                    painter = painterResource(painter),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(4.dp, Color.White, CircleShape)
                ) {
                    Text(
                        text = getFormattedNumber(raffledNumbers[index]),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }

        if (pagerState.currentPage != 0) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = 0,
                            animationSpec = tween(1000)
                        )
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(Res.string.go_to_first)
                )
            }
        }
    }
}
