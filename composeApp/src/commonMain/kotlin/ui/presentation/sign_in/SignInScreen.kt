package ui.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
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

    val supabaseClient = component.supabaseClient

    val authState = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when(result) { //handle errors
                NativeSignInResult.ClosedByUser -> TODO()
                is NativeSignInResult.Error -> TODO()
                is NativeSignInResult.NetworkError -> TODO()
                NativeSignInResult.Success -> { component.signIn() }
            }
        }
    )

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
                    authState.startFlow()

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