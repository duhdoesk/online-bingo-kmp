package ui.presentation.forgotPassword.screens

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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_unmapped_error
import themedbingo.composeapp.generated.resources.email
import themedbingo.composeapp.generated.resources.forgot_my_password
import themedbingo.composeapp.generated.resources.password_reset_info_text
import themedbingo.composeapp.generated.resources.password_reset_success
import themedbingo.composeapp.generated.resources.send_button
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.forgotPassword.event.ForgotPasswordEvent
import ui.presentation.forgotPassword.state.ForgotPasswordUIState
import ui.presentation.util.dialog.AuthErrorDialog
import ui.presentation.util.dialog.AuthSuccessDialog
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitForgotPasswordScreen(
    uiState: ForgotPasswordUIState,
    isFormValid: Boolean,
    passwordResetErrorDialogState: MutableDialogState<StringResource?>,
    passwordResetSuccessDialogState: MutableDialogState<StringResource?>,
    event: (event: ForgotPasswordEvent) -> Unit
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
                            text = stringResource(Res.string.forgot_my_password),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { input -> event(ForgotPasswordEvent.UpdateEmail(input)) },
                            label = { Text(stringResource(Res.string.email)) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(Res.string.password_reset_info_text),
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
                leftClicked = { event(ForgotPasswordEvent.PopBack) },
                rightClicked = { event(ForgotPasswordEvent.SendPasswordResetEmail) },
                rightText = Res.string.send_button,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth()
            )
        }

        if (passwordResetErrorDialogState.isVisible.value) {
            AuthErrorDialog(
                stringRes = passwordResetErrorDialogState.dialogData.value
                    ?: Res.string.auth_unmapped_error,
                onDismiss = { passwordResetErrorDialogState.hideDialog() }
            )
        }

        if (passwordResetSuccessDialogState.isVisible.value) {
            AuthSuccessDialog(
                stringRes = passwordResetSuccessDialogState.dialogData.value
                    ?: Res.string.password_reset_success,
                onDismiss = {
                    passwordResetSuccessDialogState.hideDialog()
                    event(ForgotPasswordEvent.PopBack)
                }
            )
        }
    }
}
