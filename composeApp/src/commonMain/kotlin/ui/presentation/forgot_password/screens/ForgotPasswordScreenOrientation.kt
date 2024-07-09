package ui.presentation.forgot_password.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.forgot_password.event.ForgotPasswordEvent
import ui.presentation.forgot_password.state.ForgotPasswordUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForgotPasswordScreenOrientation(
    windowInfo: WindowInfo,
    uiState: ForgotPasswordUIState,
    isFormValid: Boolean,
    passwordResetErrorDialogState: MutableDialogState<StringResource?>,
    passwordResetSuccessDialogState: MutableDialogState<StringResource?>,
    event: (event: ForgotPasswordEvent) -> Unit,
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeForgotPasswordScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                passwordResetErrorDialogState = passwordResetErrorDialogState,
                passwordResetSuccessDialogState = passwordResetSuccessDialogState,
                event = { landscapeEvent ->
                    event(landscapeEvent)
                }
            )

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitForgotPasswordScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                passwordResetErrorDialogState = passwordResetErrorDialogState,
                passwordResetSuccessDialogState = passwordResetSuccessDialogState,
                event = { portraitEvent ->
                    event(portraitEvent)
                }
            )
    }
}