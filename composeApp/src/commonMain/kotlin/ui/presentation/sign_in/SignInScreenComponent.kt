package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext
import domain.user.model.User
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    val supabaseClient: SupabaseClient,
    private val user: Flow<User?>,
    private val onSignIn: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single Coroutine Scope to handle suspend operations
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * UI State holder
     */
    private val _uiState = MutableStateFlow(SignInScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    /**
     * Function to delegate the handling of UI Events
     */
    fun uiEvent(event: SignInScreenEvent) {
        when (event) {
            SignInScreenEvent.UiLoaded -> uiLoaded()
            SignInScreenEvent.SignIn -> onSignIn()
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            user.collect { collectedUser ->
                if (collectedUser != null) onSignIn()
                else _uiState.update { state -> state.copy(isLoading = false) }
            }
        }
    }
}