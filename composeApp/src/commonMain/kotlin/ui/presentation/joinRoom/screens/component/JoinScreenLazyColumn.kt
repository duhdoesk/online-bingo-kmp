package ui.presentation.joinRoom.screens.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.BingoRoom
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.in_progress
import themedbingo.composeapp.generated.resources.not_started
import ui.presentation.joinRoom.event.JoinRoomUIEvent

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun JoinScreenLazyColumn(
    notStartedRooms: List<BingoRoom>,
    runningRooms: List<BingoRoom>,
    bingoThemes: List<BingoTheme>,
    onTapRoom: (event: JoinRoomUIEvent) -> Unit
) {
    LazyColumn {
        if (notStartedRooms.isNotEmpty()) {
            stickyHeader {
                LazyColumnStickyHeader(
                    header = Res.string.not_started,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
            }

            items(notStartedRooms) { room ->
                RoomCard(
                    room = room,
                    theme = bingoThemes.find { it.id == room.themeId },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    onClick = { onTapRoom(it) },
                    bingoType = room.type
                )
            }
        }

        if (runningRooms.isNotEmpty()) {
            stickyHeader {
                LazyColumnStickyHeader(
                    header = Res.string.in_progress,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
            }

            items(runningRooms) { room ->
                RoomCard(
                    room = room,
                    theme = bingoThemes.find { it.id == room.themeId },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    onClick = { onTapRoom(it) },
                    bingoType = room.type
                )
            }
        }
    }
}
