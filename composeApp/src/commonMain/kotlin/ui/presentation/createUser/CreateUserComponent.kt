package ui.presentation.createUser

import androidx.compose.ui.text.intl.Locale
import com.arkivanov.decompose.ComponentContext
import domain.auth.supabase.useCase.SupabaseSignOutUseCase
import domain.user.useCase.CreateUserUseCase
import domain.user.useCase.GetProfilePicturesUseCase
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
    private val signOutUseCase by inject<SupabaseSignOutUseCase>()

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
    ) { userName, message, pictureUri, profilePictures, loading ->
        val canProceed = canProceed(userName, message)

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
                ).map { resource ->
                    when (resource) {
                        is Resource.Success -> { onUserCreated() }
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
            signOutUseCase().onSuccess { onSignOut() }
        }
    }

    /** Utility Methods */
    private fun canProceed(name: String, message: String): Boolean {
        val lengths = listOf(name.length, message.length)
        return (lengths.min() >= 3)
    }

    private fun getLocalizedName(): String {
        return if (Locale.current.language.contains("pt")) {
            "Amigo Temático"
        } else {
            "Bingo Friend"
        }
    }

    private fun getLocalizedMessage(): String {
        return if (Locale.current.language.contains("pt")) {
            "O Bingo Temático é demais!"
        } else {
            "Themed Bingo is awesome!"
        }
    }

    private fun getRandomPictureUri(): String {
        val possibilities = listOf(
            "https://i.imgur.com/LGISH8Q.jpg", // hypo
            "https://i.imgur.com/zkYnGof.jpg", // koala
            "https://i.imgur.com/hbEhtXV.jpg", // jellyfish
            "https://i.imgur.com/K71gi6P.jpg", // fox
            "https://i.imgur.com/OzLeMBg.jpg", // penguin
            "https://i.imgur.com/c5H2Pqx.jpg", // lion
            "https://i.imgur.com/x1FA3r1.jpg", // flamingo
            "https://i.imgur.com/DujcYDE.jpg", // tigress
            "https://i.imgur.com/2W2zDVt.jpg", // owl
            "https://i.imgur.com/3iHYtJJ.jpg" // horse
        )

        return possibilities.shuffled().first()
    }
}
