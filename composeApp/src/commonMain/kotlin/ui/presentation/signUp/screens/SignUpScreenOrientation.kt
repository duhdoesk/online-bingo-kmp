package ui.presentation.signUp.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.signUp.event.SignUpScreenEvent
import ui.presentation.signUp.state.SignUpScreenUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreenOrientation(
    windowInfo: WindowInfo,
    uiState: SignUpScreenUIState,
    isFormValid: Boolean,
    signUpErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignUpScreenEvent) -> Unit
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeSignUpScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signUpErrorDialogState = signUpErrorDialogState
            ) { landscapeEvent ->
                event(landscapeEvent)
            }

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitSignUpScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signUpErrorDialogState = signUpErrorDialogState
            ) { landscapeEvent ->
                event(landscapeEvent)
            }
    }
}
