package ui.presentation.sign_up.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.sign_up.event.SignUpScreenEvent
import ui.presentation.sign_up.state.SignUpScreenUIState
import ui.presentation.common.RotateScreen
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LandscapeSignUpScreen(
    uiState: SignUpScreenUIState,
    isFormValid: Boolean,
    signUpErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignUpScreenEvent) -> Unit,
) {
    RotateScreen()
}