package ui.presentation.room.screens.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.character.model.Character
import ui.presentation.room.state.auxiliar.BingoStyle

@Composable
fun RaffledPresentation(
    bingoStyle: BingoStyle,
    raffled: List<String>,
) {
    when (bingoStyle) {
        is BingoStyle.Classic -> {
            RaffledItemClassic(
                raffled = raffled,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        is BingoStyle.Themed -> {
            val raffledCharacters =
                getRaffledCharacters(raffled, bingoStyle.characters)

            RaffledCharactersHorizontalPager(
                characters = raffledCharacters,
                modifier = Modifier
            )
        }
    }
}

private fun getRaffledCharacters(
    raffled: List<String>,
    characters: List<Character>,
): List<Character> {
    val raffledCharacters = mutableListOf<Character>()
    raffled.forEach { raffledId ->
        characters.find { it.id == raffledId }
            ?.let { raffledCharacters.add(it) }
    }
    return raffledCharacters.reversed()
}
