package ui.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.screens.SignInScreenOrientation
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreen(
    component: SignInScreenComponent,
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

    val signInErrorDialogState = component
        .signInErrorDialogState

    SignInScreenOrientation(
        windowInfo = windowInfo,
        uiState = uiState,
        isFormValid = isFormValid,
        signInErrorDialogState = signInErrorDialogState,
        event = { event ->
            when (event) {
                is SignInScreenEvent.SendPasswordResetEmail ->
                    component.resetPassword()

                is SignInScreenEvent.SignIn ->
                    component.signIn()

                is SignInScreenEvent.SignUp ->
                    component.signUp()

                is SignInScreenEvent.UpdateEmail ->
                    component.updateEmail(event.email)

                is SignInScreenEvent.UpdatePassword ->
                    component.updatePassword(event.password)
            }
        }
    )
}