package ui.presentation.change_password.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_unmapped_error
import themedbingo.composeapp.generated.resources.baseline_visibility_24
import themedbingo.composeapp.generated.resources.baseline_visibility_off_24
import themedbingo.composeapp.generated.resources.change_password
import themedbingo.composeapp.generated.resources.confirm_button
import themedbingo.composeapp.generated.resources.current_password
import themedbingo.composeapp.generated.resources.email
import themedbingo.composeapp.generated.resources.email_info_text
import themedbingo.composeapp.generated.resources.new_password
import themedbingo.composeapp.generated.resources.password
import themedbingo.composeapp.generated.resources.password_info_text
import themedbingo.composeapp.generated.resources.repeat_password
import themedbingo.composeapp.generated.resources.sign_up
import themedbingo.composeapp.generated.resources.sign_up_button
import ui.presentation.change_password.event.ChangePasswordScreenUIEvent
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.sign_up.event.SignUpScreenEvent
import ui.presentation.util.dialog.AuthErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitChangePasswordScreen(
    event: (event: ChangePasswordScreenUIEvent) -> Unit,
    currentPassword: String,
    newPassword: String,
    repeatPassword: String,
    isFormValid: Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordsVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (passwordsVisible) VisualTransformation.None else PasswordVisualTransformation()

    val toggleIcon =
        if (passwordsVisible) Res.drawable.baseline_visibility_off_24
        else Res.drawable.baseline_visibility_24

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(Modifier.height(16.dp))

                CreateRoomHeader(
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                ElevatedCard(
                    colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(32.dp)
                            .widthIn(max = 400.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.change_password),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f),
                            )

                            IconButton(
                                onClick = { passwordsVisible = !passwordsVisible }
                            ) {
                                Icon(
                                    painter = painterResource(toggleIcon),
                                    contentDescription = null,
                                )
                            }
                        }

                        OutlinedTextField(
                            value = currentPassword,
                            onValueChange = { input ->
                                event(ChangePasswordScreenUIEvent.CurrentPasswordTyping(input))
                            },
                            label = { Text(stringResource(Res.string.current_password)) },
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(Modifier.height(24.dp))

                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { input ->
                                event(ChangePasswordScreenUIEvent.NewPasswordTyping(input))
                            },
                            label = { Text(stringResource(Res.string.new_password)) },
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = repeatPassword,
                            onValueChange = { input ->
                                event(ChangePasswordScreenUIEvent.RepeatPasswordTyping(input))
                            },
                            label = { Text(stringResource(Res.string.repeat_password)) },
                            visualTransformation = visualTransformation,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth(),
                            isError = (newPassword != repeatPassword)
                        )

                        Text(
                            text = stringResource(Res.string.password_info_text),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            BottomButtonRow(
                rightEnabled = isFormValid,
                leftClicked = { event(ChangePasswordScreenUIEvent.PopBack) },
                rightClicked = { event(ChangePasswordScreenUIEvent.ConfirmPasswordChange) },
                rightText = Res.string.confirm_button,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth()
            )
        }
    }
}