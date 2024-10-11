package ui.presentation.room.screens.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.raffled
import ui.presentation.room.state.auxiliar.BingoStyle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RaffledAmount(
    raffled: Int,
    bingoStyle: BingoStyle,
    modifier: Modifier = Modifier,
) {
    val total = when (bingoStyle) {
        is BingoStyle.Classic -> 75
        is BingoStyle.Themed -> bingoStyle.characters.size
    }

    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.raffled) + ": ")
            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append("$raffled / $total") }
        },
        modifier = modifier,
    )
}