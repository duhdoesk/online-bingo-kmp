package ui.presentation.create_room

import com.arkivanov.decompose.ComponentContext
import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository
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
    private val onPopBack: () -> Unit,
    private val onCreateRoom: (configuration: Configuration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val getAllThemesUseCase by inject<GetAllThemesUseCase>()
    private val roomRepository by inject<BingoRoomRepository>()

    val bingoThemesList = getAllThemesUseCase()
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _uiState = MutableStateFlow(CreateScreenUiState())
    val uiState = _uiState
        .onEach {
            _isFormOk.update {
                !((_uiState.value.name.length < 4 || _uiState.value.themeId == "") ||
                        (_uiState.value.locked && _uiState.value.password.length < 4))
            }
        }
        .stateIn(
            componentContext.componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            CreateScreenUiState()
        )

    private val _isFormOk = MutableStateFlow(false)
    val isFormOk = _isFormOk.asStateFlow()

    fun updateName(name: String) {
        val errors = mutableListOf<String>()

        if (name.length in 1..3) {
            errors.add("The name must be at least 4 characters.") // refactor later
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
            errors.add("The password must be at least 4 characters.") // refactor later
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
        componentCoroutineScope().launch {
            uiState.value.run {
                roomRepository
                    .createRoom(
                        hostId = "", // refactor later
                        name = name,
                        locked = locked,
                        password = password,
                        maxWinners = maxWinners,
                        type = BingoType.THEMED,
                        themeId = themeId
                    )
                    .collect() { snapshot ->
                        if (snapshot.exists) {
                            onCreateRoom(Configuration.HostScreen(roomId = snapshot.id))
                        }
                    }
            }
        }
    }

    fun popBack() {
        onPopBack()
    }
}