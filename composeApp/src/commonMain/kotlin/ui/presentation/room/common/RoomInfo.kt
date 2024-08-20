package ui.presentation.room.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.room.model.BingoType
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_type_card
import themedbingo.composeapp.generated.resources.max_winners_card
import themedbingo.composeapp.generated.resources.room_info
import themedbingo.composeapp.generated.resources.room_name_card
import themedbingo.composeapp.generated.resources.theme_card

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RoomInfo(
    roomName: String,
    theme: BingoTheme?,
    maxWinners: Int,
    bingoType: BingoType,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.room_info),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        val cardModifier = Modifier.fillMaxWidth()

        RoomInfoCard(
            key = Res.string.room_name_card,
            value = roomName,
            modifier = cardModifier
        )

        RoomInfoCard(
            key = Res.string.bingo_type_card,
            value = stringResource(bingoType.stringResource),
            modifier = cardModifier
        )

        if (bingoType == BingoType.THEMED) {
            RoomInfoCard(
                key = Res.string.theme_card,
                value = theme!!.name,
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