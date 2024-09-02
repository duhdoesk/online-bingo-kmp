package ui.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.screens.UniqueSignInScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericErrorDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreen(
    component: SignInScreenComponent,
    windowInfo: WindowInfo,
) {
    val uiState by component.uiState.collectAsState()
    val showErrorModal by component.showErrorModal
    val showNetworkErrorModal by component.showNetworkErrorModal

    val supabaseClient = component.supabaseClient
    val authState = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = {
//            result -> component.uiEvent(SignInScreenEvent.SignInWithGoogle(result))
        }
    )

    UniqueSignInScreen(
        uiState = uiState,
        event = { component.uiEvent(it) },
        onStartGoogleAuth = { authState.startFlow() },
    )

    if (showErrorModal) {
        GenericErrorDialog(
            onDismiss = { component.showErrorModal.value = false },
            body = Res.string.unmapped_error,
        )
    }

    if (showNetworkErrorModal) {
        GenericErrorDialog(
            onDismiss = { component.showErrorModal.value = false },
            body = Res.string.unmapped_error, //todo(): refactor to network error
        )
    }
}