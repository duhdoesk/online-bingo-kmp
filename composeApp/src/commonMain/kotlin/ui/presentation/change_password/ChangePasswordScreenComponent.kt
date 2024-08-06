package ui.presentation.change_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import domain.auth.getAuthErrorDescription
import domain.auth.use_case.ChangePasswordWithReAuthenticationUseCase
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
import themedbingo.composeapp.generated.resources.password_change_success
import ui.presentation.change_password.state.ChangePasswordScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isPasswordValid
import util.componentCoroutineScope

class ChangePasswordScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentContext.componentCoroutineScope()
    private val changePasswordWithReAuthenticationUseCase by inject<ChangePasswordWithReAuthenticationUseCase>()

    private val _changePasswordScreenUIState = MutableStateFlow(ChangePasswordScreenUIState())
    val changePasswordScreenUIState = _changePasswordScreenUIState
        .onEach { state -> _isFormValid.value = checkIfFormIsValid(state) }
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(),
            ChangePasswordScreenUIState()
        )

    private val _isFormValid = mutableStateOf(false)
    val isFormValid by _isFormValid

    @OptIn(ExperimentalResourceApi::class)
    val successDialog = mutableDialogStateOf<StringResource?>(null)

    @OptIn(ExperimentalResourceApi::class)
    val errorDialog = mutableDialogStateOf<StringResource?>(null)

    private fun checkIfFormIsValid(uiState: ChangePasswordScreenUIState): Boolean {
        if (uiState.newPassword != uiState.repeatPassword)
            return false

        if (
            !uiState.currentPassword.isPasswordValid() ||
            !uiState.newPassword.isPasswordValid() ||
            !uiState.repeatPassword.isPasswordValid()
        ) return false

        return true
    }

    fun currentPasswordTyping(input: String) {
        _changePasswordScreenUIState.update { it.copy(currentPassword = input.trim()) }
    }

    fun newPasswordTyping(input: String) {
        _changePasswordScreenUIState.update { it.copy(newPassword = input.trim()) }
    }

    fun repeatPasswordTyping(input: String) {
        _changePasswordScreenUIState.update { it.copy(repeatPassword = input.trim()) }
    }

    @OptIn(ExperimentalResourceApi::class)
    fun changePassword() {
        coroutineScope.launch {
            changePasswordWithReAuthenticationUseCase
                .invoke(
                    newPassword = _changePasswordScreenUIState.value.newPassword,
                    currentPassword = _changePasswordScreenUIState.value.currentPassword
                )
                .onSuccess {
                    successDialog.showDialog(Res.string.password_change_success)
                }
                .onFailure { exception ->
                    println(exception.message)
                    val resource = getAuthErrorDescription(exception.message ?: "")
                    errorDialog.showDialog(resource)
                }
        }
    }

    fun popBack() =
        onPopBack()

}