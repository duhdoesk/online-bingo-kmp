package ui.presentation.room.component

import androidx.compose.foundation.ExperimentalFoundationApi
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.winners
import ui.presentation.room.RoomScreenUIState
import ui.presentation.room.host.screens.component.WinnerCard
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun FinishedRoomScreenComposable(
    uiState: RoomScreenUIState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
    ) {
        stickyHeader {
            Surface {
                Text(
                    text = stringResource(Res.string.winners),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val finalWinners = if (uiState.winners.size >= uiState.maxWinners) {
            uiState.winners.subList(0, uiState.maxWinners)
        } else {
            uiState.winners
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
}