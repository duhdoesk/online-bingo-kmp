package ui.feature.room.screens.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.character.model.Character
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.character_picture
import themedbingo.composeapp.generated.resources.go_to_first
import themedbingo.composeapp.generated.resources.ic_back

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun RaffledCharactersHorizontalPager(
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { characters.size }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            pageSpacing = 8.dp,
            verticalAlignment = Alignment.CenterVertically
        ) { index ->
            val pageOffset =
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset < -0.1f || pageOffset > 0.1f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
            ) {
                AsyncImage(
                    model = characters[index].pictureUri,
                    contentDescription = stringResource(Res.string.character_picture),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = characters[index].name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
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
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = stringResource(Res.string.go_to_first)
                )
            }
        }
    }
}
