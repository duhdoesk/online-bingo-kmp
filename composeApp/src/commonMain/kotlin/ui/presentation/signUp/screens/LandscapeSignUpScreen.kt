package ui.presentation.signUp.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.common.RotateScreen
import ui.presentation.signUp.event.SignUpScreenEvent
import ui.presentation.signUp.state.SignUpScreenUIState
import ui.presentation.util.dialog.dialogState.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LandscapeSignUpScreen(
    uiState: SignUpScreenUIState,
    isFormValid: Boolean,
    signUpErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignUpScreenEvent) -> Unit
) {
    RotateScreen()
}
