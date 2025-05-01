@file:OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)

package ui.presentation.room.screens.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.user.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.winners
import ui.presentation.core.SingleButtonRow
import ui.presentation.room.event.RoomPlayerEvent
import ui.presentation.room.screens.component.WinnerCard
import ui.presentation.util.getRandomLightColor

@Composable
fun PlayerScreenFinished(
    winners: List<User>,
    maxWinners: Int,
    event: (event: RoomPlayerEvent) -> Unit
) {
    Column {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            stickyHeader {
                Surface {
                    Text(
                        text = stringResource(Res.string.winners),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            val finalWinners = if (winners.size >= maxWinners) {
                winners.subList(0, maxWinners)
            } else {
                winners
            }

            itemsIndexed(finalWinners) { index, winner ->
                WinnerCard(
                    surfaceColor = getRandomLightColor(),
                    winner = winner,
                    place = index + 1,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }

        SingleButtonRow(
            onClick = { event(RoomPlayerEvent.PopBack) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}
