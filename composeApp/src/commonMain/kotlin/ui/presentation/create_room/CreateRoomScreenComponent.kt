package ui.presentation.create_room

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.room.model.BingoType
import domain.room.use_case.CreateRoomUseCase
import domain.theme.use_case.GetAllThemesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import ui.presentation.create_room.state.CreateScreenUiState
import util.componentCoroutineScope

class CreateRoomScreenComponent(
    componentContext: ComponentContext,
    private val firebaseUser: FirebaseUser,
    private val bingoType: BingoType,
    private val onPopBack: () -> Unit,
    private val onCreateRoom: (configuration: Configuration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val getAllThemesUseCase by inject<GetAllThemesUseCase>()
    private val createRoomUseCase by inject<CreateRoomUseCase>()

    val bingoThemesList = getAllThemesUseCase()
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _uiState = MutableStateFlow(CreateScreenUiState())
    val uiState = _uiState
        .onEach { state ->
            if (state.name.length < 4) {
                _isFormOk.update { false }
                return@onEach
            }
            if (bingoType == BingoType.THEMED && state.themeId == "") {
                _isFormOk.update { false }
                return@onEach
            }
            if (state.locked && state.password.length < 4) {
                _isFormOk.update { false }
                return@onEach
            }

            _isFormOk.update { true }
        }
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(),
            CreateScreenUiState()
        )

    private val _isFormOk = MutableStateFlow(false)
    val isFormOk = _isFormOk.asStateFlow()

    fun updateName(name: String) {
        val errors = mutableListOf<String>()

        if (name.length in 1..3) {
            errors.add("The name must be at least 4 characters.") //todo(): extract string res
        }

        _uiState.update {
            uiState.value.copy(
                name = name,
                nameErrors = errors
            )
        }
    }

    fun updateLocked() {
        _uiState.update {
            uiState.value.copy(
                locked = !it.locked,
                password = "",
                passwordErrors = emptyList()
            )
        }
    }

    fun updatePassword(password: String) {
        val errors = mutableListOf<String>()

        if (password.length in 1..3) {
            errors.add("The password must be at least 4 characters.") //todo(): extract string res
        }

        _uiState.update {
            uiState.value.copy(
                password = password,
                passwordErrors = errors
            )
        }
    }

    fun updateTheme(themeId: String) {
        _uiState.update {
            uiState.value.copy(
                themeId = themeId
            )
        }
    }

    fun updateMaxWinners(maxWinners: Int) {
        _uiState.update {
            uiState.value.copy(
                maxWinners = maxWinners
            )
        }
    }

    fun createRoom() {
        coroutineScope.launch {
            uiState.value.run {
                createRoomUseCase(
                    hostId = firebaseUser.uid,
                    name = name,
                    locked = locked,
                    password = password,
                    maxWinners = maxWinners,
                    type = bingoType,
                    themeId = themeId
                )
                    .onSuccess { roomId -> onCreateRoom(Configuration.HostScreen(roomId = roomId)) }
                    .onFailure {  } //todo(): display error dialog
            }
        }
    }

    fun popBack() {
        onPopBack()
    }
}