package ui.presentation.home.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.choose_your_game
import themedbingo.composeapp.generated.resources.hello
import themedbingo.composeapp.generated.resources.hw_mascot_transparent_bg

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreenHello(
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = buildAnnotatedString {
                    append("${stringResource(Res.string.hello)}, ")
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(userName)
                    }
                    append("!")
                },
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = stringResource(Res.string.choose_your_game).lowercase(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Image(
            painter = painterResource(Res.drawable.hw_mascot_transparent_bg),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(80.dp)
        )
    }
}
