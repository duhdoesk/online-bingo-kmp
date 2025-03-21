package ui.presentation.room.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_type_card
import themedbingo.composeapp.generated.resources.max_winners_card
import themedbingo.composeapp.generated.resources.room_info
import themedbingo.composeapp.generated.resources.room_name_card
import themedbingo.composeapp.generated.resources.theme_card
import ui.presentation.room.state.auxiliar.BingoStyle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RoomInfo(
    roomName: String,
    maxWinners: Int,
    bingoStyle: BingoStyle,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.room_info),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        val cardModifier = Modifier.fillMaxWidth()

        RoomInfoCard(
            key = Res.string.room_name_card,
            value = roomName,
            modifier = cardModifier
        )

        RoomInfoCard(
            key = Res.string.bingo_type_card,
            value = stringResource(bingoStyle.stringResource),
            modifier = cardModifier
        )

        if (bingoStyle is BingoStyle.Themed) {
            RoomInfoCard(
                key = Res.string.theme_card,
                value = bingoStyle.theme.name,
                modifier = cardModifier
            )
        }

        RoomInfoCard(
            key = Res.string.max_winners_card,
            value = maxWinners.toString(),
            modifier = cardModifier
        )
    }
}
