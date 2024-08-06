package ui.presentation.change_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import ui.presentation.change_password.state.ChangePasswordScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isPasswordValid
import util.componentCoroutineScope

class ChangePasswordScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentContext.componentCoroutineScope()

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

    val successDialog = mutableDialogStateOf(null)
    val errorDialog = mutableDialogStateOf(null)

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

    fun changePassword() {

    }

    fun popBack() =
        onPopBack()

}