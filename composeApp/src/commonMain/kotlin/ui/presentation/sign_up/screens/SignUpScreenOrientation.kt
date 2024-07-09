package ui.presentation.sign_up.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.sign_up.event.SignUpScreenEvent
import ui.presentation.sign_up.state.SignUpScreenUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreenOrientation(
    windowInfo: WindowInfo,
    uiState: SignUpScreenUIState,
    isFormValid: Boolean,
    signUpErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignUpScreenEvent) -> Unit,
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeSignUpScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signUpErrorDialogState = signUpErrorDialogState,
            ) { landscapeEvent ->
                event(landscapeEvent)
            }

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitSignUpScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signUpErrorDialogState = signUpErrorDialogState,
            ) { landscapeEvent ->
                event(landscapeEvent)
            }
    }
}