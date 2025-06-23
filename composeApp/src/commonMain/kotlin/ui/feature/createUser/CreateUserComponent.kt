package ui.feature.createUser

import com.arkivanov.decompose.ComponentContext
import domain.feature.auth.useCase.SignOutUseCase
import domain.feature.user.model.getLocalizedMessage
import domain.feature.user.model.getLocalizedName
import domain.feature.user.model.getRandomPictureUri
import domain.feature.user.useCase.CreateUserUseCase
import domain.feature.user.useCase.GetProfilePicturesUseCase
import domain.profilePictures.model.ProfilePictures
import domain.util.resource.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class CreateUserComponent(
    componentContext: ComponentContext,
    private val onSignOut: () -> Unit,
    private val onUserCreated: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val supabaseClient: SupabaseClient by inject()
    private val createUserUseCase: CreateUserUseCase by inject()
    private val getProfilePicturesUseCase: GetProfilePicturesUseCase by inject()
    private val signOutUseCase: SignOutUseCase by inject()

    private val _userInfo: Flow<UserInfo?> = supabaseClient
        .auth
        .sessionStatus
        .map { status ->
            when (status) {
                is SessionStatus.Authenticated -> status.session.user
                else -> null
            }
        }

    private val _name = MutableStateFlow(getLocalizedName())
    private val _message = MutableStateFlow(getLocalizedMessage())
    private val _pictureUri = MutableStateFlow(getRandomPictureUri())
    private val _loading = MutableStateFlow(false)

    val uiState: StateFlow<CreateUserUiState> = combine(
        _name,
        _message,
        _pictureUri,
        getProfilePicturesUseCase(),
        _loading
    ) { userName, message, pictureUri, profilePicturesRes, loading ->
        val canProceed = canProceed(userName, message)
        val profilePictures = profilePicturesRes.getOrNull() ?: ProfilePictures(emptyList())

        CreateUserUiState(
            loading = loading,
            name = userName,
            message = message,
            pictureUri = pictureUri,
            profilePictures = profilePictures,
            canProceed = canProceed
        )
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), CreateUserUiState())

    private val _uiError = Channel<CreateUserUiError>()
    val uiError = _uiError.receiveAsFlow()

    /**
     * Delegates each UI event to the responsible function
     */
    fun onUiEvent(event: CreateUserUiEvent) {
        when (event) {
            CreateUserUiEvent.CreateUser -> createUser()
            CreateUserUiEvent.SignOut -> signOut()
            is CreateUserUiEvent.UpdateName -> updateName(event.name)
            is CreateUserUiEvent.UpdatePicture -> updatePicture(event.pictureUri)
            is CreateUserUiEvent.UpdateVictoryMessage -> updateMessage(event.message)
        }
    }

    /** Methods to handle UI Events */
    private fun createUser() {
        coroutineScope.launch {
            _loading.update { true }

            combine(
                _userInfo,
                _name,
                _message,
                _pictureUri
            ) { userInfo, name, message, pictureUri ->
                if (userInfo == null) {
                    _loading.update { false }
                    return@combine
                }

                createUserUseCase.invoke(
                    id = userInfo.id,
                    email = userInfo.email.orEmpty(),
                    name = name,
                    pictureUri = pictureUri,
                    victoryMessage = message
                ).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            onUserCreated()
                        }

                        is Resource.Failure -> {
                            _uiError.send(CreateUserUiError(resource.cause.name))
                        }
                    }
                }

                _loading.update { false }
            }.collect()
        }
    }

    private fun updateName(name: String) {
        _name.update { name }
    }

    private fun updateMessage(message: String) {
        _message.update { message }
    }

    private fun updatePicture(pictureUri: String) {
        _pictureUri.update { pictureUri }
    }

    private fun signOut() {
        coroutineScope.launch {
            signOutUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        onSignOut()
                    }

                    is Resource.Failure -> {
                        _uiError.send(CreateUserUiError(resource.cause.name))
                    }
                }
            }
        }
    }

    /** Utility Methods */
    private fun canProceed(name: String, message: String): Boolean {
        val lengths = listOf(name.length, message.length)
        return (lengths.min() >= 3)
    }
}
