package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.getAuthErrorDescription
import domain.auth.use_case.DeleteAccountUseCase
import domain.auth.use_case.SignOutUseCase
import domain.user.use_case.FlowUserUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.delete_account_success
import themedbingo.composeapp.generated.resources.sign_out_error
import themedbingo.composeapp.generated.resources.update_nickname_failure
import themedbingo.composeapp.generated.resources.update_victory_failure
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class ProfileScreenComponent(
    componentContext: ComponentContext,
    val firebaseUser: FirebaseUser,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit,
    private val onUpdatePicture: () -> Unit,
    private val onUpdatePassword: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val signOutUseCase by inject<SignOutUseCase>()
    private val deleteAccountUseCase by inject<DeleteAccountUseCase>()
    private val updateNameUseCase by inject<UpdateNameUseCase>()
    private val updateVictoryMessageUseCase by inject<UpdateVictoryMessageUseCase>()

    private val flowUserUseCase by inject<FlowUserUseCase>()

    private val _userFlow = flowUserUseCase.invoke(firebaseUser.uid)

    private val _profileScreenUiState = combine(_userFlow) {
        user ->
        if (user.isEmpty()) ProfileScreenUIState.Error
        else ProfileScreenUIState.Success(user.first())
    }

    val profileScreenUiState = _profileScreenUiState
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(),
            ProfileScreenUIState.Loading
        )

    @OptIn(ExperimentalResourceApi::class)
    val successDialogState = mutableDialogStateOf<StringResource?>(null)

    @OptIn(ExperimentalResourceApi::class)
    val errorDialogState = mutableDialogStateOf<StringResource?>(null)
    val updateNameDialogState = mutableDialogStateOf("")
    val updateVictoryMessageDialogState = mutableDialogStateOf("")
    val signOutDialogState = mutableDialogStateOf(null)

    val deleteAccountDialogState = mutableDialogStateOf(null)

    fun popBack() {
        onPopBack()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun signOut() =
        coroutineScope.launch {
            signOutUseCase.invoke()
                .onSuccess { onSignOut() }
                .onFailure { errorDialogState.showDialog(Res.string.sign_out_error) }
        }

    @OptIn(ExperimentalResourceApi::class)
    fun deleteAccount() {
        coroutineScope.launch {
            deleteAccountUseCase.invoke()
                .onSuccess {
                    successDialogState.showDialog(Res.string.delete_account_success)
                    onSignOut()
                }
                .onFailure { exception ->
                    val body = getAuthErrorDescription(exception.message ?: "")
                    errorDialogState.showDialog(body)
                }
        }
    }

    fun updatePicture() {
        onUpdatePicture()
    }

    fun updatePassword() {
        onUpdatePassword()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun updateName(newName: String) {
        coroutineScope.launch {
            updateNameUseCase.invoke(
                userId = firebaseUser.uid,
                newName = newName
            )
                .onFailure { errorDialogState.showDialog(Res.string.update_nickname_failure) }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    fun updateVictoryMessage(newVictoryMessage: String) {
        coroutineScope.launch {
            updateVictoryMessageUseCase.invoke(
                userId = firebaseUser.uid,
                newVictoryMessage = newVictoryMessage
            )
                .onFailure { errorDialogState.showDialog(Res.string.update_victory_failure) }
        }
    }
}
