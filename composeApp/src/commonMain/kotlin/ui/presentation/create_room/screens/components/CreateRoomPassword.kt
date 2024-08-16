package ui.presentation.create_room.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_lock_24
import themedbingo.composeapp.generated.resources.baseline_lock_open_24
import themedbingo.composeapp.generated.resources.key_icon
import themedbingo.composeapp.generated.resources.password_textField
import themedbingo.composeapp.generated.resources.private_session_checkbox
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SessionPasswordComponent(
    modifier: Modifier = Modifier,
    uiState: CreateScreenUiState,
    leadingIconModifier: Modifier,
    onUpdateLockedState: (locked: Boolean) -> Unit,
    onUpdatePassword: (password: String) -> Unit
) {
    Column(modifier = modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.locked,
                onCheckedChange = { onUpdateLockedState(it) })
            Text(text = stringResource(resource = Res.string.private_session_checkbox))
        }

        Row(verticalAlignment = Alignment.Bottom) {

            val color by remember { mutableStateOf(getRandomLightColor()) }

            Box(modifier = leadingIconModifier) {
                Surface(
                    color = color,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val icon =
                        if (uiState.locked) Res.drawable.baseline_lock_24 else Res.drawable.baseline_lock_open_24

                    Icon(
                        painter = painterResource(resource = icon),
                        tint = Color.Black,
                        contentDescription = stringResource(resource = Res.string.key_icon),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = uiState.password,
                    enabled = uiState.locked,
                    label = { Text(text = stringResource(resource = Res.string.password_textField)) },
                    onValueChange = { onUpdatePassword(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            for (error in uiState.passwordErrors) {
                Text(
                    text = stringResource(error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}