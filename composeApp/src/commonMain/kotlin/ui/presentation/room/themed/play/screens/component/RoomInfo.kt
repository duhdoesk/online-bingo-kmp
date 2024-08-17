package ui.presentation.room.themed.play.screens.component

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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_type_card
import themedbingo.composeapp.generated.resources.classic_card
import themedbingo.composeapp.generated.resources.max_winners_card
import themedbingo.composeapp.generated.resources.room_info
import themedbingo.composeapp.generated.resources.room_name_card
import themedbingo.composeapp.generated.resources.theme_card
import themedbingo.composeapp.generated.resources.themed_card
import ui.presentation.room.common.RoomInfoCard
import ui.presentation.room.themed.play.state.PlayScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RoomInfo(
    uiState: PlayScreenUIState,
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
            value = uiState.roomName,
            modifier = cardModifier
        )

        val bingoType = when (uiState.bingoType) {
            BingoType.CLASSIC -> Res.string.classic_card
            BingoType.THEMED -> Res.string.themed_card
        }

        RoomInfoCard(
            key = Res.string.bingo_type_card,
            value = stringResource(bingoType),
            modifier = cardModifier
        )

        if (uiState.bingoType == BingoType.THEMED) {
            RoomInfoCard(
                key = Res.string.theme_card,
                value = uiState.theme!!.name,
                modifier = cardModifier
            )
        }

        RoomInfoCard(
            key = Res.string.max_winners_card,
            value = uiState.maxWinners.toString(),
            modifier = cardModifier
        )
    }
}