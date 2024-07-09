package ui.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomButtonRow(
    leftEnabled: Boolean = true,
    rightEnabled: Boolean = true,
    leftClicked: () -> Unit,
    rightClicked: () -> Unit,
    leftText: StringResource = Res.string.back_button,
    rightText: StringResource,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            enabled = leftEnabled,
            onClick = { leftClicked() },
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(leftText)
            )

            Text(
                text = stringResource(leftText),
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            enabled = rightEnabled,
            onClick = { rightClicked() },
        ) {
            Text(
                text = stringResource(rightText),
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(rightText)
            )
        }
    }
}