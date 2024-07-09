package ui.presentation.forgot_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.forgot_password.event.ForgotPasswordEvent
import ui.presentation.forgot_password.screens.ForgotPasswordScreenOrientation
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForgotPasswordScreen(
    component: ForgotPasswordScreenComponent,
    windowInfo: WindowInfo
) {
    val uiState = component
        .uiState
        .collectAsState()
        .value

    val isFormValid = component
        .isFormValid
        .collectAsState()
        .value

    val passwordResetErrorDialogState = component
        .passwordResetErrorDialogState

    val passwordResetSuccessDialogState = component
        .passwordResetSuccessDialogState

    ForgotPasswordScreenOrientation(
        windowInfo = windowInfo,
        uiState = uiState,
        isFormValid = isFormValid,
        passwordResetErrorDialogState = passwordResetErrorDialogState,
        passwordResetSuccessDialogState = passwordResetSuccessDialogState,
        event = { event ->
            when (event) {
                is ForgotPasswordEvent.SendPasswordResetEmail ->
                    component.sendPasswordResetEmail()

                is ForgotPasswordEvent.UpdateEmail ->
                    component.updateEmail(event.email)

                is ForgotPasswordEvent.PopBack ->
                    component.popBack()
            }
        }
    )
}