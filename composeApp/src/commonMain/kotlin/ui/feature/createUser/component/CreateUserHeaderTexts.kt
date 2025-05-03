package ui.feature.createUser.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_user_screen_body
import themedbingo.composeapp.generated.resources.create_user_screen_title
import ui.feature.core.text.OutlinedShadowedText
import ui.theme.createUserOnColor
import ui.theme.createUserPrimaryColor

@Composable
fun ColumnScope.CreateUserHeaderTexts(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedShadowedText(
            text = stringResource(Res.string.create_user_screen_title),
            fontSize = 32,
            strokeWidth = 2f,
            fontColor = createUserPrimaryColor,
            strokeColor = createUserOnColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )

        Text(
            text = stringResource(Res.string.create_user_screen_body),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
        )
    }
}
