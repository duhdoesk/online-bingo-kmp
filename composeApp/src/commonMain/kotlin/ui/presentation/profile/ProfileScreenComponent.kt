package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import domain.auth.getAuthErrorDescription
import domain.auth.supabase.useCase.SupabaseDeleteAccountUseCase
import domain.auth.supabase.useCase.SupabaseSignOutUseCase
import domain.user.model.User
import domain.user.useCase.DeleteUserUseCase
import domain.user.useCase.GetProfilePicturesUseCase
import domain.user.useCase.ObserveUser
import domain.user.useCase.UpdateNameUseCase
import domain.user.useCase.UpdateUserPictureUseCase
import domain.user.useCase.UpdateVictoryMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.sign_out_error
import themedbingo.composeapp.generated.resources.unmapped_error
import themedbingo.composeapp.generated.resources.update_nickname_failure
import themedbingo.composeapp.generated.resources.update_victory_failure
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.dialog.dialogState.mutableDialogStateOf
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class ProfileScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit
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
    private val updatePictureUseCase: UpdateUserPictureUseCase by inject()

    /**
     * UI State Use Cases
     */
    private val observeUser: ObserveUser by inject()
    private val getProfilePicturesUseCase: GetProfilePicturesUseCase by inject()

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

            is ProfileScreenEvent.UpdatePicture -> updatePicture(event.newPictureUri)
            is ProfileScreenEvent.UpdateName -> updateName(event.newName)
            is ProfileScreenEvent.UpdateMessage -> updateMessage(event.newMessage)
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            val userId = user.first()?.id

            userId?.let {
                combine(observeUser(userId), getProfilePicturesUseCase()) { collectedUser, pics ->
                    val error = (collectedUser == null)

                    ProfileScreenUIState(
                        isLoading = false,
                        user = collectedUser,
                        error = error,
                        profilePictures = pics
                    )
                }.collect { state ->
                    _uiState.update { state }
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

    private fun updatePicture(newPictureUri: String) {
        coroutineScope.launch {
            if (newPictureUri.isEmpty()) return@launch

            val currentPictureUri = uiState.value.user?.pictureUri.orEmpty()
            if (currentPictureUri == newPictureUri) return@launch

            uiState.value.user?.run {
                updatePictureUseCase(
                    userId = id,
                    pictureUri = newPictureUri
                ).onFailure { errorDialogState.showDialog(Res.string.unmapped_error) }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun updateName(newName: String) {
        uiState.value.user?.run {
            coroutineScope.launch {
                updateNameUseCase.invoke(
                    userId = id,
                    newName = newName
                ).onFailure { errorDialogState.showDialog(Res.string.update_nickname_failure) }
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
                ).onFailure { errorDialogState.showDialog(Res.string.update_victory_failure) }
            }
        }
    }
}
