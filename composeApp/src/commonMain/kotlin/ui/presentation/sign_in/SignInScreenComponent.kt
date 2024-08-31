package ui.presentation.sign_in

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import domain.user.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    val supabaseClient: SupabaseClient,
    private val onSignIn: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single Coroutine Scope to handle suspend operations
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * UI State holder
     */
    private val _uiState = MutableStateFlow(SignInScreenUIState())
    val uiState = _uiState.asStateFlow()

    /**
     * Modal triggers
     */
    val showErrorModal = mutableStateOf(false)
    val showNetworkErrorModal = mutableStateOf(false)

    /**
     * Function to delegate the handling of UI Events
     */
    fun uiEvent(event: SignInScreenEvent) {
        when (event) {
            SignInScreenEvent.UiLoaded -> uiLoaded()
            is SignInScreenEvent.SignInWithGoogle -> signIn(event.result)
            is SignInScreenEvent.SignInWithApple -> {
                //todo(): apple login
            }
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            user.collect {
                if (it != null) onSignIn()
            }
        }
    }

    private fun signIn(result: NativeSignInResult) {
        componentCoroutineScope().launch {
            when(result) {
                NativeSignInResult.ClosedByUser -> showErrorModal.value = true
                is NativeSignInResult.Error -> showErrorModal.value = true
                is NativeSignInResult.NetworkError -> showNetworkErrorModal.value = true
                NativeSignInResult.Success -> onSignIn()
            }
        }
    }
}