package ui.presentation.create_room.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.password_textField
import themedbingo.composeapp.generated.resources.private_session_checkbox

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomEditPassword(
    currentPassword: String?,
    isLocked: Boolean,
    updateLockedState: () -> Unit,
    updateCurrentPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier,
    ) {
        Checkbox(
            checked = isLocked,
            onCheckedChange = { updateLockedState() },
        )

        Text(
            text = stringResource(resource = Res.string.private_session_checkbox),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }

    AnimatedVisibility(
        visible = isLocked,
        enter = expandVertically(tween(400)),
        exit = shrinkVertically(tween(400)),
    ) {
        CreateRoomEditStringCard(
            label = Res.string.password_textField,
            currentInfo = currentPassword.orEmpty(),
            onClick = { updateCurrentPassword() },
            modifier = modifier,
        )
    }
}