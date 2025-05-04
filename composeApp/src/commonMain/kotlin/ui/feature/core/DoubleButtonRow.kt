package ui.feature.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DoubleButtonRow(
    leftEnabled: Boolean = true,
    leftClicked: () -> Unit,
    leftText: StringResource = Res.string.back_button,
    rightEnabled: Boolean = true,
    rightClicked: () -> Unit,
    rightText: StringResource,
    rightButtonIcon: ImageVector? = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Row(
            modifier = modifier
                .padding(bottom = bottomPadding)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (leftEnabled) leftClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(leftText)
                )

                Text(
                    text = stringResource(leftText),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (rightEnabled) rightClicked()
                }
            ) {
                Text(
                    text = stringResource(rightText),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                if (rightButtonIcon != null) {
                    Icon(
                        imageVector = rightButtonIcon,
                        contentDescription = stringResource(rightText)
                    )
                }
            }
        }
    }
}
