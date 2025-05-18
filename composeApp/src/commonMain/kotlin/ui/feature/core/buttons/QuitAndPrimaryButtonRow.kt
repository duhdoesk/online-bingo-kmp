package ui.feature.core.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_button
import ui.theme.createUserOnColor
import ui.theme.createUserPrimaryColor
import ui.theme.createUserSecondaryColor

@Composable
fun QuitAndPrimaryButtonRow(
    primaryButtonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = createUserPrimaryColor,
        contentColor = createUserOnColor,
        disabledContainerColor = Color.LightGray
    ),
    iconButtonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = createUserSecondaryColor,
        contentColor = createUserOnColor
    ),
    primaryText: String = stringResource(Res.string.create_button),
    primaryEnabled: Boolean = true,
    quitEnabled: Boolean = true,
    onPrimaryClick: () -> Unit,
    onQuitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        CustomIconButton(
            colors = iconButtonColors,
            onClick = onQuitClick,
            buttonSize = 60.dp,
            enabled = quitEnabled
        )

        CustomPrimaryButton(
            text = primaryText,
            colors = primaryButtonColors,
            onClick = onPrimaryClick,
            height = 60.dp,
            enabled = primaryEnabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
