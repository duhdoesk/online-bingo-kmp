package ui.presentation.sign_in.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState
import ui.presentation.util.RotateScreen
import ui.presentation.util.dialog.dialog_state.MutableDialogState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LandscapeSignInScreen(
    uiState: SignInScreenUIState,
    isFormValid: Boolean,
    signInErrorDialogState: MutableDialogState<StringResource?>,
    event: (event: SignInScreenEvent) -> Unit,
) {
    RotateScreen()
}