package ui.feature.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithApple
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_network_error
import themedbingo.composeapp.generated.resources.bg_splash
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.feature.core.dialog.GenericErrorDialog
import ui.feature.signIn.component.SignInButton
import ui.feature.signIn.component.SignInHeader
import util.Log

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreen(component: SignInScreenComponent) {
    /** Modal visibility state holders */
    var showNetworkErrorModal by remember { mutableStateOf(false) }
    var showUnmappedErrorModal by remember { mutableStateOf(false) }

    /** Supabase Client instance to handle sign in attempts */
    val supabaseClient = component.supabaseClient

    /** Handles Google Sign In */
    val googleSignIn = supabaseClient
        .composeAuth
        .rememberSignInWithGoogle(
            onResult = { result ->
                handleSignInResult(
                    result = result,
                    onSuccess = { component.signIn() },
                    onFailure = { error ->
                        when (error) {
                            SignInError.NETWORK -> {
                                showNetworkErrorModal = true
                            }

                            SignInError.UNMAPPED -> {
                                showUnmappedErrorModal = true
                            }
                        }
                    }
                )
            }
        )

    /** Handles Apple Sign In */
    val appleSignIn = supabaseClient
        .composeAuth
        .rememberSignInWithApple(
            onResult = { result ->
                handleSignInResult(
                    result = result,
                    onSuccess = { component.signIn() },
                    onFailure = { error ->
                        when (error) {
                            SignInError.NETWORK -> {
                                showNetworkErrorModal = true
                            }

                            SignInError.UNMAPPED -> {
                                showUnmappedErrorModal = true
                            }
                        }
                    }
                )
            }
        )

    /** Displays Sign In screen if there is no user authenticated  */
    SignInScreen(
        onStartGoogleAuth = { googleSignIn.startFlow() },
        onStartAppleAuth = { appleSignIn.startFlow() }
    )

    /** Shows error modal */
    if (showUnmappedErrorModal) {
        GenericErrorDialog(
            onDismiss = { showUnmappedErrorModal = false },
            body = Res.string.unmapped_error
        )
    }

    /** Shows network error modal */
    if (showNetworkErrorModal) {
        GenericErrorDialog(
            onDismiss = { showNetworkErrorModal = false },
            body = Res.string.auth_network_error
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SignInScreen(
    onStartGoogleAuth: () -> Unit,
    onStartAppleAuth: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SignInHeader()

            Spacer(Modifier.height(48.dp))

            SignInButton(
                onStartGoogleAuth = onStartGoogleAuth,
                onStartAppleAuth = onStartAppleAuth,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .widthIn(min = 240.dp, max = 360.dp)
                    .height(48.dp)
            )
        }
    }
}

private fun handleSignInResult(
    result: NativeSignInResult,
    onSuccess: () -> Unit,
    onFailure: (error: SignInError) -> Unit
) {
    val tag = "SignInScreen"
    var logMessage = ""

    when (result) {
        is NativeSignInResult.Success -> {
            logMessage = "Sign in successful"
            onSuccess()
        }

        is NativeSignInResult.NetworkError -> {
            logMessage = result.message
            onFailure(SignInError.NETWORK)
        }

        is NativeSignInResult.Error -> {
            logMessage = result.message
            onFailure(SignInError.UNMAPPED)
        }

        is NativeSignInResult.ClosedByUser -> {
            logMessage = "Sign in closed by user"
            onFailure(SignInError.UNMAPPED)
        }
    }

    Log.d(
        tag = tag,
        message = logMessage
    )
}
