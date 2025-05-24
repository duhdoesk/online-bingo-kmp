@file:OptIn(ExperimentalCoroutinesApi::class)

package ui.feature.profile

import com.arkivanov.decompose.ComponentContext
import domain.feature.auth.useCase.SignOutUseCase
import domain.feature.user.useCase.DeleteUserUseCase
import domain.feature.user.useCase.GetCurrentUserUseCase
import domain.feature.user.useCase.UpdateUserNameUseCase
import domain.feature.user.useCase.UpdateUserPictureUseCase
import domain.feature.user.useCase.UpdateUserVictoryMessageUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.profile_change_picture_failure
import themedbingo.composeapp.generated.resources.profile_change_username_failure
import themedbingo.composeapp.generated.resources.profile_change_victory_message_failure
import themedbingo.composeapp.generated.resources.profile_error
import themedbingo.composeapp.generated.resources.sign_out_error
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class ProfileScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /** Single Coroutine Scope to handle suspend operations */
    private val coroutineScope = componentCoroutineScope()

    /** Action Use Cases */
    private val getCurrentUserUseCase: GetCurrentUserUseCase by inject()
    private val signOutUseCase: SignOutUseCase by inject()
    private val deleteUserUseCase: DeleteUserUseCase by inject()
    private val updatePictureUseCase: UpdateUserPictureUseCase by inject()
    private val updateUserNameUseCase: UpdateUserNameUseCase by inject()
    private val updateVictoryMessageUseCase: UpdateUserVictoryMessageUseCase by inject()

    /** Channel for emitting error messages to the UI */
    private val _messages = Channel<String>()
    val messages = _messages.receiveAsFlow()

    /** UI State holder */
    private val _retry = Channel<Unit>()
    val uiState: StateFlow<ProfileScreenUiState> = _retry
        .receiveAsFlow()
        .onStart { emit(Unit) }
        .flatMapLatest {
            getCurrentUserUseCase.invoke()
                .map { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            ProfileScreenUiState.Success(resource.data)
                        }

                        is Resource.Failure -> {
                            ProfileScreenUiState.Error(getString(Res.string.profile_error))
                        }
                    }
                }
                .onStart { emit(ProfileScreenUiState.Loading) }
        }
        .stateIn(
            scope = coroutineScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileScreenUiState.Loading
        )

    /** Function to delegate the responsibility for each UI Event */
    fun uiEvent(event: ProfileScreenUiEvent) {
        coroutineScope.launch {
            when (event) {
                ProfileScreenUiEvent.DeleteAccount -> deleteAccount()
                ProfileScreenUiEvent.PopBack -> popBack()
                ProfileScreenUiEvent.Retry -> _retry.send(Unit)
                ProfileScreenUiEvent.SignOut -> signOut()
                is ProfileScreenUiEvent.UpdatePicture -> updatePicture(event.pictureUrl)
                is ProfileScreenUiEvent.UpdateName -> updateName(event.name)
                is ProfileScreenUiEvent.UpdateMessage -> updateMessage(event.message)
            }
        }
    }

    /** Functions to handle each UI Event */
    private fun popBack() {
        onPopBack()
    }

    private suspend fun signOut() {
        val resource = signOutUseCase.invoke().first()
        when (resource) {
            is Resource.Success -> onSignOut()
            is Resource.Failure -> _messages.send(getString(Res.string.sign_out_error))
        }
    }

    private suspend fun deleteAccount() {
        when (val state = uiState.value) {
            is ProfileScreenUiState.Success -> {
                val resource = deleteUserUseCase.invoke(state.user.id).first()
                when (resource) {
                    is Resource.Success -> onSignOut()
                    is Resource.Failure -> _messages.send(getString(Res.string.sign_out_error))
                }
            }

            else -> return
        }
    }

    private suspend fun updatePicture(newPic: String) {
        val resource = updatePictureUseCase.invoke(newPic).first()
        if (resource is Resource.Failure) {
            _messages.send(getString(Res.string.profile_change_picture_failure))
        }
    }

    private suspend fun updateName(newName: String) {
        val resource = updateUserNameUseCase.invoke(newName).first()
        if (resource is Resource.Failure) {
            _messages.send(getString(Res.string.profile_change_username_failure))
        }
    }

    private suspend fun updateMessage(newMessage: String) {
        val resource = updateVictoryMessageUseCase.invoke(newMessage).first()
        if (resource is Resource.Failure) {
            _messages.send(getString(Res.string.profile_change_victory_message_failure))
        }
    }
}
