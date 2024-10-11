@file:OptIn(ExperimentalResourceApi::class)

package ui.presentation.room.state.auxiliar

import domain.character.model.Character
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.classic_bingo
import themedbingo.composeapp.generated.resources.themed_bingo

sealed class BingoStyle @OptIn(ExperimentalResourceApi::class) constructor(val stringResource: StringResource) {
    /**
     * Represents the classic bingo, played with numbers
     */
    data object Classic : BingoStyle(stringResource = Res.string.classic_bingo)

    /**
     * Represents our original bingo style, played with themes and characters
     */
    data class Themed(
        val theme: BingoTheme,
        val characters: List<Character>
    ) : BingoStyle(stringResource = Res.string.themed_bingo)
}