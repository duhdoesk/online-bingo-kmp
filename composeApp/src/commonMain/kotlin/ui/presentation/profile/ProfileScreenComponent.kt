package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.auth.getAuthErrorDescription
import domain.auth.supabase.useCase.SupabaseDeleteAccountUseCase
import domain.auth.supabase.useCase.SupabaseSignOutUseCase
import domain.user.useCase.DeleteUserUseCase
import domain.user.useCase.GetProfilePicturesUseCase
import domain.user.useCase.GetSignedInUserUseCase
import domain.user.useCase.UpdateNameUseCase
import domain.user.useCase.UpdateUserPictureUseCase
import domain.user.useCase.UpdateVictoryMessageUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
import ui.presentation.core.dialog.dialogState.mutableDialogStateOf
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.state.ProfileScreenUIState
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class ProfileScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Audio Player
     */
    private val audioPlayer: AudioPlayer by inject()

    /**
     * Single Coroutine Scope to handle suspend operations
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Action Use Cases
     */
    private val getSignedInUserUseCase by inject<GetSignedInUserUseCase>()
    private val signOutUseCase by inject<SupabaseSignOutUseCase>()
    private val deleteAccountUseCase by inject<SupabaseDeleteAccountUseCase>()
    private val updateNameUseCase by inject<UpdateNameUseCase>()
    private val updateVictoryMessageUseCase by inject<UpdateVictoryMessageUseCase>()
    private val deleteUserUseCase by inject<DeleteUserUseCase>()
    private val updatePictureUseCase: UpdateUserPictureUseCase by inject()

    /**
     * UI State Use Cases
     */
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
            combine(getSignedInUserUseCase(), getProfilePicturesUseCase()) { collectedUser, pics ->
                val uiState = when (collectedUser) {
                    is Resource.Success -> {
                        ProfileScreenUIState(
                            isLoading = false,
                            user = collectedUser.data,
                            error = false,
                            profilePictures = pics
                        )
                    }

                    is Resource.Failure -> {
                        ProfileScreenUIState(
                            isLoading = false,
                            user = null,
                            error = true,
                            profilePictures = pics
                        )
                    }
                }

                _uiState.update { uiState }
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
                .onSuccess {
                    audioPlayer.stop()
                    onSignOut()
                }
                .onFailure { errorDialogState.showDialog(Res.string.sign_out_error) }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun deleteAccount() {
        coroutineScope.launch {
            val uid = uiState.value.user!!.id
            deleteAccountUseCase.invoke(uid)
                .onSuccess {
                    audioPlayer.stop()
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
