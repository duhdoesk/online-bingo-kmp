package ui.presentation.join_room.screens.component

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
import domain.theme.model.BingoTheme
import ui.presentation.join_room.event.JoinRoomUIEvent

@Composable
fun RoomCard(
    room: BingoRoom,
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
                AsyncImage(
                    model = theme?.pictureUri,
                    contentDescription = "Theme Picture",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = room.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = "Tema: ${theme?.name}",
                        style = bodyStyle,
                    )

                    Text(
                        text = "Jogadores: ${room.players.size}",
                        style = bodyStyle,
                    )
                }

                if (room.locked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        modifier = Modifier.padding(16.dp).size(32.dp)
                    )
                }
            }
        }

    }


}