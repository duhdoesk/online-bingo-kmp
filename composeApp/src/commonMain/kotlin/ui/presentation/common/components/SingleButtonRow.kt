package ui.presentation.common.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SingleButtonRow(
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Row(
            modifier = modifier
                .padding(bottom = bottomPadding)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (enabled) onClick()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(Res.string.back_button)
                )

                Text(
                    text = stringResource(Res.string.back_button),
                    modifier = Modifier.padding(horizontal = 6.dp),
                )
            }

            Spacer(Modifier.weight(1f))
        }
    }
}