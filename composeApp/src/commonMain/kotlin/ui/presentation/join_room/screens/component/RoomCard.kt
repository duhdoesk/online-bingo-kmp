package ui.presentation.join_room.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_balls
import themedbingo.composeapp.generated.resources.locked
import themedbingo.composeapp.generated.resources.players_card
import themedbingo.composeapp.generated.resources.theme_picture
import themedbingo.composeapp.generated.resources.theme_textField
import ui.presentation.join_room.event.JoinRoomUIEvent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RoomCard(
    room: BingoRoom,
    bingoType: BingoType,
    theme: BingoTheme?,
    modifier: Modifier = Modifier,
    onClick: (event: JoinRoomUIEvent) -> Unit,
) {
    val bodyStyle = MaterialTheme.typography.bodyLarge

    Column(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.clickable { onClick(JoinRoomUIEvent.TapRoom(room)) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (bingoType) {
                    BingoType.CLASSIC ->
                        Image(
                            painter = painterResource(Res.drawable.bingo_balls),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                        )

                    BingoType.THEMED ->
                        AsyncImage(
                            model = theme?.pictureUri,
                            contentDescription = stringResource(Res.string.theme_picture),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                        )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = room.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    if (theme != null) {
                        Text(
                            text = "${stringResource(Res.string.theme_textField)}: ${theme.name}",
                            style = bodyStyle,
                        )
                    }

                    Text(
                        text = "${stringResource(Res.string.players_card)}: ${room.players.size}",
                        style = bodyStyle,
                    )
                }

                if (room.locked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(Res.string.locked),
                        modifier = Modifier.padding(16.dp).size(32.dp)
                    )
                }
            }
        }
    }
}