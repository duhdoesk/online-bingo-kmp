package ui.presentation.forgotPassword.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.forgotPassword.event.ForgotPasswordEvent
import ui.presentation.forgotPassword.state.ForgotPasswordUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForgotPasswordScreenOrientation(
    windowInfo: WindowInfo,
    uiState: ForgotPasswordUIState,
    isFormValid: Boolean,
    passwordResetErrorDialogState: MutableDialogState<StringResource?>,
    passwordResetSuccessDialogState: MutableDialogState<StringResource?>,
    event: (event: ForgotPasswordEvent) -> Unit
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
