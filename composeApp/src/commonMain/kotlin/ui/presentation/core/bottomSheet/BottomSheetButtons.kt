package ui.presentation.core.bottomSheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.cancel_button
import themedbingo.composeapp.generated.resources.confirm_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomSheetButtons(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    canConfirm: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        TextButton(
            onClick = { onCancel() }
        ) {
            Text(text = stringResource(Res.string.cancel_button))
        }

        Spacer(Modifier.weight(1f))

        TextButton(
            onClick = { onConfirm() },
            enabled = canConfirm
        ) {
            Text(text = stringResource(Res.string.confirm_button))
        }
    }
}
