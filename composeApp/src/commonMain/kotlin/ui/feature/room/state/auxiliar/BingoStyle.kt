@file:OptIn(ExperimentalResourceApi::class)

package ui.feature.room.state.auxiliar

import domain.character.model.Character
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.classic_bingo_type
import themedbingo.composeapp.generated.resources.themed_bingo_type

@OptIn(ExperimentalResourceApi::class)
sealed class BingoStyle constructor(val stringResource: StringResource) {
    /**
     * Represents the classic bingo, played with numbers
     */
    data object Classic : BingoStyle(stringResource = Res.string.classic_bingo_type)

    /**
     * Represents our original bingo style, played with themes and characters
     */
    data class Themed(
        val theme: BingoTheme,
        val characters: List<Character>
    ) : BingoStyle(stringResource = Res.string.themed_bingo_type)
}
