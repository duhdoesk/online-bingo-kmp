package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext
import domain.auth.AuthService
import domain.auth.getAuthErrorDescription
import domain.user.model.User
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
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.password_reset_success
import ui.presentation.sign_in.state.SignInScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isEmailValid
import ui.presentation.util.isPasswordValid
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    user: User?,
    private val onSignIn: () -> Unit,
    private val onSignUp: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

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

    @OptIn(ExperimentalResourceApi::class)
    val passwordResetSuccessDialogState = mutableDialogStateOf<StringResource?>(null)

    init {
        if (user != null) onSignIn()
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
                try {
                    authService
                        .authenticate(email, password)
                        .user?.let { onSignIn() }

                } catch (e: Exception) {
                    println(e.cause)
                    println(e.message)

                    signInErrorDialogState.showDialog(getAuthErrorDescription(e.message.orEmpty()))
                    clearUiState()
                }
            }
        }
    }

    fun signUp() =
        onSignUp()

    @OptIn(ExperimentalResourceApi::class)
    fun sendPasswordResetEmail() {
        uiState.value.run {
            componentCoroutineScope().launch {
                try {
                    authService
                        .sendPasswordResetEmail(email)

                    passwordResetSuccessDialogState.showDialog(Res.string.password_reset_success)

                } catch (e: Exception) {
                    println(e.cause)
                    println(e.message)

                    signInErrorDialogState.showDialog(getAuthErrorDescription(e.message.orEmpty()))
                }
            }
        }

        clearUiState()
    }

    private fun clearUiState() {
        _uiState.update { state ->
            state.copy(
                email = "",
                password = ""
            )
        }
    }
}