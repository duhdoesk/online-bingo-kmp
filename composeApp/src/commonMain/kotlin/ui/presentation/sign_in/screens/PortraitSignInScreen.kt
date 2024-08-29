package ui.presentation.sign_in.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.email
import themedbingo.composeapp.generated.resources.forgot_my_password
import themedbingo.composeapp.generated.resources.or
import themedbingo.composeapp.generated.resources.password
import themedbingo.composeapp.generated.resources.sign_in
import themedbingo.composeapp.generated.resources.sign_up
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState
import ui.presentation.util.dialog.AuthErrorDialog
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitSignInScreen(
    uiState: SignInScreenUIState,
    isFormValid: Boolean,
    signInErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignInScreenEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
                .verticalScroll(rememberScrollState())
        ) {
            CreateRoomHeader(
                modifier = Modifier.padding(bottom = 16.dp, top = 32.dp)
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
                        text = stringResource(Res.string.sign_in),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { input ->
                            event(SignInScreenEvent.UpdateEmail(input))
                        },
                        label = { Text(stringResource(Res.string.email)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { input ->
                            event(SignInScreenEvent.UpdatePassword(input))
                        },
                        label = { Text(stringResource(Res.string.password)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            event(SignInScreenEvent.SignIn)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                    ) {
                        Text(stringResource(Res.string.sign_in))
                    }

                    Spacer(Modifier.height(4.dp))

                    TextButton(
                        onClick = {
                            focusManager.clearFocus()
                            event(SignInScreenEvent.SendPasswordResetEmail)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(stringResource(Res.string.forgot_my_password))
                    }
                }
            }

            Text(text = stringResource(Res.string.or))

            ElevatedCard(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp)
                    .padding(horizontal = 24.dp)
                    .clickable {
                        focusManager.clearFocus()
                        event(SignInScreenEvent.SignUp)
                    }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(32.dp)
                        .widthIn(max = 400.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.sign_up),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f),
                    )

                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = stringResource(Res.string.sign_up)
                    )
                }
            }
        }

        if (signInErrorDialogState.isVisible.value) {
            signInErrorDialogState.dialogData.value?.let { stringResource ->
                AuthErrorDialog(
                    stringRes = stringResource,
                    onDismiss = { signInErrorDialogState.hideDialog() }
                )
            }
        }
    }
}