package ui.presentation.sign_in.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreenOrientation(
    windowInfo: WindowInfo,
    uiState: SignInScreenUIState,
    isFormValid: Boolean,
    signInErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignInScreenEvent) -> Unit,
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeSignInScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signInErrorDialogState = signInErrorDialogState,
            ) { landscapeEvent ->
                event(landscapeEvent)
            }

        WindowInfo.DeviceOrientation.Portrait ->
            PortraitSignInScreen(
                uiState = uiState,
                isFormValid = isFormValid,
                signInErrorDialogState = signInErrorDialogState,
            ) { portraitEvent ->
                event(portraitEvent)
            }
    }
}