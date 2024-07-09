package ui.presentation.forgot_password

import com.arkivanov.decompose.ComponentContext
import domain.auth.AuthService
import domain.auth.getAuthErrorDescription
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
import ui.presentation.forgot_password.state.ForgotPasswordUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isEmailValid
import util.componentCoroutineScope

class ForgotPasswordScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

    private val _uiState = MutableStateFlow(ForgotPasswordUIState())
    val uiState = _uiState
        .onEach { state ->
            _isFormValid.update { state.email.isEmailValid() }
        }
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            ForgotPasswordUIState()
        )


    private val _isFormValid = MutableStateFlow(false)
    val isFormValid = _isFormValid
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            false
        )

    @OptIn(ExperimentalResourceApi::class)
    val passwordResetErrorDialogState = mutableDialogStateOf<StringResource?>(null)

    @OptIn(ExperimentalResourceApi::class)
    val passwordResetSuccessDialogState = mutableDialogStateOf<StringResource?>(null)

    fun popBack() =
        onPopBack()

    fun updateEmail(email: String) {
        _uiState.update { state ->
            state.copy(email = email.trim())
        }
    }

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

                    passwordResetErrorDialogState.showDialog(getAuthErrorDescription(e.message.orEmpty()))
                }
            }
        }
    }
}