@file:OptIn(ExperimentalResourceApi::class)

package domain.room.model

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.classic_bingo_type
import themedbingo.composeapp.generated.resources.themed_bingo_type

enum class BingoType (val stringResource: StringResource) {
    CLASSIC(stringResource = Res.string.classic_bingo_type),
    THEMED(stringResource = Res.string.themed_bingo_type),
}