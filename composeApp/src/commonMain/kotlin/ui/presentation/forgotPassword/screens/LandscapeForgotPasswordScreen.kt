package ui.presentation.forgotPassword.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.common.RotateScreen
import ui.presentation.forgotPassword.event.ForgotPasswordEvent
import ui.presentation.forgotPassword.state.ForgotPasswordUIState
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LandscapeForgotPasswordScreen(
    uiState: ForgotPasswordUIState,
    isFormValid: Boolean,
    passwordResetErrorDialogState: MutableDialogState<StringResource?>,
    passwordResetSuccessDialogState: MutableDialogState<StringResource?>,
    event: (event: ForgotPasswordEvent) -> Unit
) {
    RotateScreen()
}
