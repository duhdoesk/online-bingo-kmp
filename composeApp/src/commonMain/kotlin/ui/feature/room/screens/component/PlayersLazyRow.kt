package ui.feature.room.screens.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.feature.user.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.host
import themedbingo.composeapp.generated.resources.player_picture
import themedbingo.composeapp.generated.resources.players_card

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayersLazyRow(
    players: List<User>,
    winners: List<User>,
    host: User?,
    contentSize: Dp = 60.dp,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        host?.let {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.host),
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )

                    Spacer(Modifier.height(4.dp))

                    UserContent(
                        player = host,
                        contentSize = contentSize
                    )
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .weight(1f)
                .animateContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.players_card) + ": ${players.size}",
                    fontWeight = FontWeight.SemiBold
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    itemsIndexed(winners) { index, winner ->
                        UserContent(
                            player = winner,
                            winner = true,
                            contentSize = contentSize,
                            place = index + 1
                        )
                    }

                    val losers = players.filterNot { it in winners }
                    items(losers) { loser ->
                        UserContent(loser, contentSize)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UserContent(
    player: User,
    contentSize: Dp,
    winner: Boolean = false,
    place: Int = 0
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            val borderColor = when (winner) {
                true -> MaterialTheme.colorScheme.secondary
                false -> Color.Transparent
            }

            Canvas(
                modifier = Modifier
                    .size((contentSize.value * 1.2).dp)
                    .border(
                        border = BorderStroke(3.dp, borderColor),
                        shape = CircleShape
                    ),
                onDraw = { drawCircle(color = Color.Transparent) }
            )

            AsyncImage(
                model = player.pictureUri,
                contentDescription = stringResource(Res.string.player_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(contentSize)
                    .clip(CircleShape)
            )

            if (winner) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    val containerColor = MaterialTheme.colorScheme.secondary

                    Canvas(
                        modifier = Modifier.size(30.dp),
                        onDraw = { drawCircle(color = containerColor) }
                    )

                    Text(
                        text = place.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }

        Text(
            text = player.name,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            minLines = 1,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(contentSize)
        )
    }
}
