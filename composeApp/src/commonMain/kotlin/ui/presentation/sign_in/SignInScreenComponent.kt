package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.AuthService
import domain.auth.getAuthErrorDescription
import domain.auth.use_case.AuthenticateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.sign_in.state.SignInScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isEmailValid
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    firebaseUser: FirebaseUser?,
    private val onSignIn: () -> Unit,
    private val onSignUp: () -> Unit,
    private val onPasswordReset: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()
    private val authenticateUserUseCase by inject<AuthenticateUserUseCase>()

    private val _uiState = MutableStateFlow(SignInScreenUIState())
    val uiState = _uiState
        .onEach { state ->
            _isFormValid.update { state.email.isEmailValid() && state.password.length >= 8 }
        }
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            SignInScreenUIState()
        )

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid = _isFormValid
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            false
        )

    @OptIn(ExperimentalResourceApi::class)
    val signInErrorDialogState = mutableDialogStateOf<StringResource?>(null)

    init {
        if (firebaseUser != null) onSignIn()
    }

    fun updateEmail(email: String) {
        _uiState.update { state ->
            state.copy(email = email.trim())
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { state ->
            state.copy(password = password.trim())
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    fun signIn() {
        uiState.value.run {
            componentCoroutineScope().launch {
                authenticateUserUseCase.invoke(
                    email = email,
                    password = password
                )
                    .onSuccess { onSignIn() }
                    .onFailure { exception ->
                        signInErrorDialogState.showDialog(getAuthErrorDescription(exception.message.orEmpty()))
                        clearPassword()
                    }
            }
        }
    }

    fun signUp() =
        onSignUp()

    fun resetPassword() =
        onPasswordReset()

    private fun clearPassword() {
        _uiState.update { state ->
            state.copy(
                password = ""
            )
        }
    }
}