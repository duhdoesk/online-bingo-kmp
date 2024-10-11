package ui.presentation.room.screens.component

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
import domain.user.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.winners
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun FinishedRoomScreenComposable(
    winners: List<User>,
    maxWinners: Int,
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
}