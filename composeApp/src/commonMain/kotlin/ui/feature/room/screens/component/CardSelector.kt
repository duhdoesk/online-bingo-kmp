package ui.feature.room.screens.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.feature.room.state.auxiliar.BingoStyle
import ui.feature.room.state.auxiliar.CardState

@Composable
fun CardSelector(
    cardState: CardState.Success,
    bingoStyle: BingoStyle
) {
    when (bingoStyle) {
        is BingoStyle.Classic -> CardSelectorClassic(
            cardState = cardState,
            modifier = Modifier.padding(16.dp).fillMaxSize()
        )

        is BingoStyle.Themed -> CardSelectorThemed(
            cardState = cardState,
            characters = bingoStyle.characters,
            modifier = Modifier.padding(16.dp).fillMaxSize()
        )
    }
}
