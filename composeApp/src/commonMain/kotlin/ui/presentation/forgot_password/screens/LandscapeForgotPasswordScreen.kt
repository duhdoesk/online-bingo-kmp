package ui.presentation.forgot_password.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.forgot_password.event.ForgotPasswordEvent
import ui.presentation.forgot_password.state.ForgotPasswordUIState
import ui.presentation.common.RotateScreen
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LandscapeForgotPasswordScreen(
    uiState: ForgotPasswordUIState,
    isFormValid: Boolean,
    passwordResetErrorDialogState: MutableDialogState<StringResource?>,
    passwordResetSuccessDialogState: MutableDialogState<StringResource?>,
    event: (event: ForgotPasswordEvent) -> Unit,
) {
    RotateScreen()
}