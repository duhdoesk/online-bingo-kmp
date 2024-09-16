package ui.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithApple
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_network_error
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.presentation.common.LoadingScreen
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.screens.UniqueSignInScreen
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreen(
    component: SignInScreenComponent,
) {
    /**
     * UI State
     */
    val uiState by component.uiState.collectAsState()

    /**
     * Informs the component/view model that the UI is ready
     */
    LaunchedEffect(Unit) { component.uiEvent(SignInScreenEvent.UiLoaded) }

    /**
     * Modal visibility state holders
     */
    var showErrorModal by remember { mutableStateOf(false) }
    var showNetworkErrorModal by remember { mutableStateOf(false) }

    /**
     * Supabase Client instance to handle sign in attempts
     */
    val supabaseClient = component.supabaseClient

    /**
     * Handles Google Sign In
     */
    val googleSignIn = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> component.uiEvent(SignInScreenEvent.SignIn)

                is NativeSignInResult.NetworkError -> {
                    (result.message)
                    showNetworkErrorModal = true
                }

                is NativeSignInResult.Error -> {
                    println(result.message)
                    showErrorModal = true
                }

                else -> {
                    println("Closed by User")
                    showErrorModal = true
                }
            }
        }
    )

    /**
     * Handles Apple Sign In
     */
    val appleSignIn = supabaseClient.composeAuth.rememberSignInWithApple(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> {
                    component.uiEvent(SignInScreenEvent.SignIn)
                }

                is NativeSignInResult.NetworkError -> {
                    println(result.message)
                    showNetworkErrorModal = true
                }

                is NativeSignInResult.Error -> {
                    println(result.message)
                    showErrorModal = true
                }

                else -> {
                    println("Closed by User")
                    showErrorModal = true
                }
            }
        }
    )

    /**
     * Displays Loading Screen if UI State is loading still
     */
    if (uiState.isLoading) {
        LoadingScreen()
        return
    }

    /**
     * Displays Sign In screen if there is no user authenticted
     */
    UniqueSignInScreen(
        onStartGoogleAuth = { googleSignIn.startFlow() },
        onStartAppleAuth = { appleSignIn.startFlow() },
    )

    /**
     * Shows error modal
     */
    if (showErrorModal) {
        GenericErrorDialog(
            onDismiss = { showErrorModal = false },
            body = Res.string.unmapped_error,
        )
    }

    /**
     * Shows network error modal
     */
    if (showNetworkErrorModal) {
        GenericErrorDialog(
            onDismiss = { showNetworkErrorModal = false },
            body = Res.string.auth_network_error,
        )
    }
}