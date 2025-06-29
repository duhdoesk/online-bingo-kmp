package ui.feature.createRoom.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.RoomPrivacy
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.password_textField
import themedbingo.composeapp.generated.resources.private_session_checkbox
import ui.feature.core.cards.SingleInfoEditCard
import ui.feature.core.text.OutlinedShadowedText

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EditPasswordCard(
    privacy: RoomPrivacy,
    onPasswordClick: () -> Unit,
    onPrivacyChange: (RoomPrivacy) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedShadowedText(
                text = stringResource(resource = Res.string.private_session_checkbox),
                fontSize = 24,
                strokeWidth = 2f,
                fontColor = MaterialTheme.colorScheme.onSecondary,
                strokeColor = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.width(8.dp))

            Switch(
                checked = privacy is RoomPrivacy.Private,
                onCheckedChange = { onPrivacyChange(privacy.switch()) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSecondary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.secondary
                )
            )
        }

        AnimatedVisibility(
            visible = privacy is RoomPrivacy.Private,
            enter = expandVertically(tween(400)),
            exit = shrinkVertically(tween(400))
        ) {
            SingleInfoEditCard(
                label = stringResource(Res.string.password_textField),
                currentValue = (privacy as? RoomPrivacy.Private)?.password ?: "",
                onClick = onPasswordClick,
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
