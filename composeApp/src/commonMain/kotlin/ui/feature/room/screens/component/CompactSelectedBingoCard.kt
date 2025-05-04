package ui.feature.room.screens.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.feature.room.state.auxiliar.BingoStyle
import ui.feature.room.state.auxiliar.CardState

@Composable
fun CompactSelectedBingoCard(
    bingoStyle: BingoStyle,
    cardState: CardState.Success,
    raffled: List<String>
) {
    when (bingoStyle) {
        is BingoStyle.Classic -> CompactSelectedBingoCardClassic(
            cardState = cardState,
            raffled = raffled,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        is BingoStyle.Themed -> CompactSelectedBingoCardThemed(
            cardState = cardState,
            characters = bingoStyle.characters,
            raffled = raffled,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
