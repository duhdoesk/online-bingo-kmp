package ui.presentation.create_user

import com.arkivanov.decompose.ComponentContext
import domain.user.use_case.CreateUserUseCase
import domain.user.use_case.GetProfilePicturesUseCase
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.create_user.event.CreateUserEvent
import ui.presentation.create_user.state.CreateUserState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class CreateUserViewModel(
    componentContext: ComponentContext,
    private val sessionStatus: Flow<SessionStatus>,
    private val onSignOut: () -> Unit,
    private val onUserCreated: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Coroutine Scope to handle suspend operations
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Use Cases
     */
    private val createUserUseCase: CreateUserUseCase by inject()
    private val getProfilePicturesUseCase: GetProfilePicturesUseCase by inject()

    /**
     * Screen State holder
     */
    private val _screenState = MutableStateFlow(CreateUserState.IDLE)
    val screenState = _screenState.asStateFlow()

    /**
     * Error Modal visibility holder
     */
    val errorDialogState = mutableDialogStateOf<String?>(null)

    /**
     * Delegates each UI event to the responsible function
     */
    fun uiEvent(event: CreateUserEvent) {
        when (event) {
            CreateUserEvent.CreateUser -> createUser()
            CreateUserEvent.SignOut -> onSignOut()
            CreateUserEvent.UiLoaded -> uiLoaded()
            is CreateUserEvent.UpdateName -> updateName(event.name)
            is CreateUserEvent.UpdatePicture -> updatePicture(event.pictureUri)
            is CreateUserEvent.UpdateVictoryMessage -> updateMessage(event.message)
        }
    }

    /**
     * Loads available profile pictures
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            combine(
                sessionStatus,
                getProfilePicturesUseCase(),
            ) { status, pics ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        _screenState.update {
                            it.copy(
                                profilePictures = pics,
                                processing = false,
                                userInfo = status.session.user
                            )
                        }
                    }

                    else -> {
                        errorDialogState.showDialog("User is not authenticated")
                        _screenState.update {
                            it.copy(
                                profilePictures = pics,
                                processing = false
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    /**
     * Validates info and creates new user
     */
    private fun createUser() {
        coroutineScope.launch {
            _screenState.update { it.copy(processing = true) }

            val userInfo = screenState.value.userInfo

            if (userInfo?.email == null) {
                _screenState.update { it.copy(processing = false) }
                return@launch
            }

            val state = screenState.value

            createUserUseCase(
                id = userInfo.id,
                email = userInfo.email!!,
                name = state.name,
                pictureUri = state.pictureUri,
                victoryMessage = state.message,
            ).fold(
                onSuccess = {
                    _screenState.update { it.copy(processing = false) }
                    onUserCreated()
                },
                onFailure = { exception ->
                    _screenState.update { it.copy(processing = false) }
                    errorDialogState.showDialog("${exception.cause} - ${exception.message}")
                },
            )
        }
    }

    private fun updateMessage(message: String) {
        _screenState.update { it.copy(message = message) }
    }

    private fun updatePicture(pictureUri: String) {
        _screenState.update { it.copy(pictureUri = pictureUri) }
    }

    private fun updateName(name: String) {
        _screenState.update { it.copy(name = name) }
    }
}