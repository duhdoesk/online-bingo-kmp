package ui.presentation.signUp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_unmapped_error
import themedbingo.composeapp.generated.resources.email
import themedbingo.composeapp.generated.resources.email_info_text
import themedbingo.composeapp.generated.resources.password
import themedbingo.composeapp.generated.resources.password_info_text
import themedbingo.composeapp.generated.resources.repeat_password
import themedbingo.composeapp.generated.resources.sign_up
import themedbingo.composeapp.generated.resources.sign_up_button
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.signUp.event.SignUpScreenEvent
import ui.presentation.signUp.state.SignUpScreenUIState
import ui.presentation.util.dialog.AuthErrorDialog
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitSignUpScreen(
    uiState: SignUpScreenUIState,
    isFormValid: Boolean,
    signUpErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignUpScreenEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                        Text(
                            text = stringResource(Res.string.sign_up),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { input ->
                                event(SignUpScreenEvent.UpdateEmail(input))
                            },
                            label = { Text(stringResource(Res.string.email)) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(Res.string.email_info_text),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        )

                        Spacer(Modifier.height(24.dp))

                        OutlinedTextField(
                            value = uiState.password1,
                            onValueChange = { input ->
                                event(SignUpScreenEvent.UpdatePassword1(input))
                            },
                            label = { Text(stringResource(Res.string.password)) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.password2,
                            onValueChange = { input ->
                                event(SignUpScreenEvent.UpdatePassword2(input))
                            },
                            label = { Text(stringResource(Res.string.repeat_password)) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth(),
                            isError = (uiState.password1 != uiState.password2)
                        )

                        Text(
                            text = stringResource(Res.string.password_info_text),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            DoubleButtonRow(
                rightEnabled = isFormValid,
                leftClicked = { event(SignUpScreenEvent.PopBack) },
                rightClicked = { event(SignUpScreenEvent.SignUp) },
                rightText = Res.string.sign_up_button,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth()
            )
        }

        if (signUpErrorDialogState.isVisible.value) {
            AuthErrorDialog(
                stringRes = signUpErrorDialogState.dialogData.value
                    ?: Res.string.auth_unmapped_error,
                onDismiss = { signUpErrorDialogState.hideDialog() }
            )
        }
    }
}
