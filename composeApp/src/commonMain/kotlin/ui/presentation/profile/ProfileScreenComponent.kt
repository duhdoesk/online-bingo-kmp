package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import domain.auth.getAuthErrorDescription
import domain.auth.supabase.use_case.SupabaseDeleteAccountUseCase
import domain.auth.supabase.use_case.SupabaseSignOutUseCase
import domain.auth.use_case.DeleteAccountUseCase
import domain.auth.use_case.SignOutUseCase
import domain.user.model.User
import domain.user.use_case.DeleteUserUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class ProfileScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit,
    private val onUpdatePicture: () -> Unit,
    private val onUpdatePassword: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single Coroutine Scope to handle suspend operations
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Action Use Cases
     */
    private val signOutUseCase by inject<SupabaseSignOutUseCase>()
    private val deleteAccountUseCase by inject<SupabaseDeleteAccountUseCase>()
    private val updateNameUseCase by inject<UpdateNameUseCase>()
    private val updateVictoryMessageUseCase by inject<UpdateVictoryMessageUseCase>()
    private val deleteUserUseCase by inject<DeleteUserUseCase>()

    /**
     * UI State holder
     */
    private val _uiState = MutableStateFlow(ProfileScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    /**
     * Result Modals visibility holders
     */
    val successDialogState = mutableDialogStateOf<StringResource?>(null)
    val errorDialogState = mutableDialogStateOf<StringResource?>(null)

    /**
     * Function to delegate the responsibility for each UI Event
     */
    fun uiEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.DeleteAccount -> deleteAccount()
            ProfileScreenEvent.PopBack -> popBack()
            ProfileScreenEvent.SignOut -> signOut()
            ProfileScreenEvent.UILoaded -> uiLoaded()
            ProfileScreenEvent.UpdatePassword -> updatePassword()
            ProfileScreenEvent.UpdatePicture -> updatePicture()

            is ProfileScreenEvent.UpdateName -> updateName(event.newName)
            is ProfileScreenEvent.UpdateMessage -> updateMessage(event.newMessage)
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            user.collect { collectedUser ->
                _uiState.update {
                    ProfileScreenUIState(
                        isLoading = false,
                        user = collectedUser,
                    )
                }
            }
        }
    }

    private fun popBack() {
        onPopBack()
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun signOut() {
        coroutineScope.launch {
            signOutUseCase.invoke()
                .onSuccess { onSignOut() }
                .onFailure { errorDialogState.showDialog(Res.string.sign_out_error) }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun deleteAccount() {
        coroutineScope.launch {
            val uid = uiState.value.user!!.id
            deleteAccountUseCase.invoke(uid)
                .onSuccess {
                    deleteUserUseCase(uid)
                    onSignOut()
                }
                .onFailure { exception ->
                    println(exception.cause)
                    println(exception.message)
                    val body = getAuthErrorDescription(exception.message ?: "")
                    errorDialogState.showDialog(body)
                }
        }
    }

    private fun updatePicture() {
        onUpdatePicture()
    }

    private fun updatePassword() {
        onUpdatePassword()
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun updateName(newName: String) {
        uiState.value.user?.run {
            coroutineScope.launch {
                updateNameUseCase.invoke(
                    userId = id,
                    newName = newName
                )
                    .onSuccess { uiLoaded() }
                    .onFailure { errorDialogState.showDialog(Res.string.update_nickname_failure) }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun updateMessage(newVictoryMessage: String) {
        uiState.value.user?.run {
            coroutineScope.launch {
                updateVictoryMessageUseCase.invoke(
                    userId = id,
                    newVictoryMessage = newVictoryMessage
                )
                    .onSuccess { uiLoaded() }
                    .onFailure { errorDialogState.showDialog(Res.string.update_victory_failure) }
            }
        }
    }
}
