package ui.feature.lobby.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil3.compose.AsyncImage
import domain.room.model.BingoRoom
import domain.room.model.RoomPrivacy
import domain.room.model.mockBingoRoom
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.ic_lock
import themedbingo.composeapp.generated.resources.in_progress
import themedbingo.composeapp.generated.resources.max_winners
import themedbingo.composeapp.generated.resources.not_started
import ui.feature.core.text.OutlinedShadowedText
import ui.feature.room.state.auxiliar.BingoState
import ui.theme.AppTheme
import ui.theme.LilitaOneFontFamily
import ui.theme.PoppinsFontFamily
import ui.theme.lobbyOnColor
import ui.theme.lobbySecondaryColor
import ui.theme.surface

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsLazyColumn(
    rooms: List<BingoRoom> = emptyList(),
    onRoomClicked: (BingoRoom) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        val thereAreNotStartedRooms = rooms.any { it.state == BingoState.NOT_STARTED }
        val thereAreRunningRooms = rooms.any { it.state == BingoState.RUNNING }

        if (thereAreNotStartedRooms) {
            stickyHeader {
                CustomStickyHeader(
                    text = stringResource(Res.string.not_started),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        items(rooms.filter { it.state == BingoState.NOT_STARTED }) { room ->
            RoomCard(
                room = room,
                onClick = { onRoomClicked(room) }
            )
        }

        if (thereAreNotStartedRooms && thereAreRunningRooms) {
            item { Spacer(Modifier.height(2.dp)) }
        }

        if (thereAreRunningRooms) {
            stickyHeader {
                CustomStickyHeader(
                    text = stringResource(Res.string.in_progress),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        items(rooms.filter { it.state == BingoState.RUNNING }) { room ->
            RoomCard(
                room = room,
                onClick = { onRoomClicked(room) }
            )
        }
    }
}

@Composable
private fun CustomStickyHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .shadow(3.dp, RoundedCornerShape(bottomEnd = 16.dp))
            .clip(RoundedCornerShape(bottomEnd = 16.dp))
            .background(lobbySecondaryColor)
    ) {
        OutlinedShadowedText(
            text = text,
            fontSize = 24,
            strokeWidth = 2f,
            fontColor = lobbyOnColor,
            strokeColor = lobbySecondaryColor,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun RoomCard(
    room: BingoRoom,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var columnSize by remember { mutableStateOf(Size.Zero) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = surface,
            contentColor = lobbySecondaryColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = lobbySecondaryColor
        ),
        shape = RoundedCornerShape(bottomEnd = 16.dp),
        modifier = modifier,
        onClick = onClick,
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (room.type is BingoRoom.Type.Themed) {
                AsyncImage(
                    model = room.type.theme.pictureUri,
                    placeholder = painterResource(Res.drawable.hot_water_logo),
                    error = painterResource(Res.drawable.hot_water_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    clipToBounds = true,
                    modifier = Modifier
                        .size(with(LocalDensity.current) { columnSize.height.toDp() + 32.dp })
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .onGloballyPositioned {
                        columnSize = it.size.toSize()
                    }
            ) {
                Text(
                    text = room.name.uppercase(),
                    color = lobbySecondaryColor,
                    fontSize = 20.sp,
                    fontFamily = LilitaOneFontFamily(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = room.host.name,
                    color = lobbySecondaryColor,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontFamily = PoppinsFontFamily(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = stringResource(Res.string.max_winners) + ": ${room.maxWinners}",
                    color = lobbySecondaryColor,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontFamily = PoppinsFontFamily(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (room.privacy is RoomPrivacy.Private) {
                Icon(
                    painter = painterResource(Res.drawable.ic_lock),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val rooms = List(2) { mockBingoRoom().copy(type = BingoRoom.Type.Classic) } +
        List(2) {
            mockBingoRoom().copy(
                type = BingoRoom.Type.Classic,
                privacy = RoomPrivacy.Private("1234")
            )
        } +
        List(4) { mockBingoRoom().copy(privacy = RoomPrivacy.Private("1234")) }

    AppTheme {
        Surface {
            Column {
                RoomsLazyColumn(
                    rooms = rooms,
                    onRoomClicked = {}
                )
            }
        }
    }
}
