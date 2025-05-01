package ui.presentation.createRoom

import com.arkivanov.decompose.ComponentContext
import domain.room.model.BingoType
import domain.room.useCase.CreateRoomUseCase
import domain.theme.model.BingoTheme
import domain.theme.useCase.ObserveAvailableThemes
import domain.user.useCase.GetSignedInUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.name_length_error
import themedbingo.composeapp.generated.resources.password_length_error
import ui.navigation.Configuration
import ui.presentation.core.dialog.dialogState.mutableDialogStateOf
import ui.presentation.createRoom.event.CreateScreenEvent
import ui.presentation.createRoom.state.CreateScreenUiState
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class CreateRoomScreenComponent(
    componentContext: ComponentContext,
    private val bingoType: BingoType,
    private val onPopBack: () -> Unit,
    private val onCreateRoom: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Coroutine Scope to handle each suspend operation
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Use Cases
     */
    private val createRoomUseCase by inject<CreateRoomUseCase>()
    private val observeAvailableThemes: ObserveAvailableThemes by inject()
    private val getSignedInUserUseCase: GetSignedInUserUseCase by inject()

    /**
     * Modal visibility holders
     */
    val showErrorDialog = mutableDialogStateOf<StringResource?>(null)

    /**
     * UI State holder
     */
    private val _uiState = MutableStateFlow(CreateScreenUiState.INITIAL)
    val uiState = _uiState
        .onEach { state ->
            if (state.name.length < 4) {
                _isFormOk.update { false }
                return@onEach
            }
            if (bingoType == BingoType.THEMED && state.selectedTheme == null) {
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
            CreateScreenUiState.INITIAL
        )

    /**
     * Holds a check if the user inputted all the info necessary to crete the room.
     */
    private val _isFormOk = MutableStateFlow(false)
    val isFormOk = _isFormOk.asStateFlow()

    /**
     * Delegate the responsibility for the handling of each UI Event
     */
    fun uiEvent(event: CreateScreenEvent) {
        when (event) {
            is CreateScreenEvent.CreateRoom -> createRoom()
            is CreateScreenEvent.PopBack -> popBack()
            is CreateScreenEvent.UILoaded -> uiLoaded()
            is CreateScreenEvent.UpdateLocked -> updateLocked()
            is CreateScreenEvent.UpdateMaxWinners -> updateMaxWinners(event.maxWinners)
            is CreateScreenEvent.UpdateName -> updateName(event.name)
            is CreateScreenEvent.UpdatePassword -> updatePassword(event.password)
            is CreateScreenEvent.UpdateTheme -> updateTheme(event.theme)
        }
    }

    /**
     * Functions to handle each of the UI Events
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            when (bingoType) {
                BingoType.CLASSIC -> {
                    _uiState.update {
                        CreateScreenUiState(
                            loading = false,
                            name = "",
                            nameErrors = listOf(),
                            locked = false,
                            password = "",
                            passwordErrors = listOf(),
                            availableThemes = listOf(),
                            maxWinners = 1,
                            bingoType = bingoType,
                            selectedTheme = null
                        )
                    }
                }

                BingoType.THEMED -> {
                    observeAvailableThemes().collect { themes ->
                        _uiState.update {
                            CreateScreenUiState(
                                loading = false,
                                name = "",
                                nameErrors = listOf(),
                                locked = false,
                                password = "",
                                passwordErrors = listOf(),
                                availableThemes = themes,
                                maxWinners = 1,
                                bingoType = bingoType,
                                selectedTheme = themes.first()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateName(name: String) {
        val errors = mutableListOf<StringResource>()

        if (name.length in 1..3) {
            errors.add(Res.string.name_length_error)
        }

        _uiState.update {
            uiState.value.copy(
                name = name,
                nameErrors = errors
            )
        }
    }

    private fun updateLocked() {
        _uiState.update {
            uiState.value.copy(
                locked = !it.locked,
                password = "",
                passwordErrors = emptyList()
            )
        }
    }

    private fun updatePassword(password: String) {
        val errors = mutableListOf<StringResource>()

        if (password.length in 1..3) {
            errors.add(Res.string.password_length_error)
        }

        _uiState.update {
            uiState.value.copy(
                password = password,
                passwordErrors = errors
            )
        }
    }

    private fun updateTheme(theme: BingoTheme) {
        _uiState.update {
            uiState.value.copy(
                selectedTheme = theme
            )
        }
    }

    private fun updateMaxWinners(maxWinners: Int) {
        _uiState.update {
            uiState.value.copy(
                maxWinners = maxWinners
            )
        }
    }

    private fun createRoom() {
        coroutineScope.launch {
            getSignedInUserUseCase().collect { collectedUser ->
                uiState.value.run {
                    createRoomUseCase(
                        hostId = collectedUser.getOrNull()?.id ?: "",
                        name = name,
                        locked = locked,
                        password = password,
                        maxWinners = maxWinners,
                        type = bingoType,
                        themeId = selectedTheme?.id.orEmpty()
                    )
                        .onSuccess { roomId ->
                            val config = when (bingoType) {
                                BingoType.CLASSIC -> Configuration.HostScreenClassic(roomId)
                                BingoType.THEMED -> Configuration.HostScreenThemed(roomId)
                            }
                            onCreateRoom(config)
                        }
                        .onFailure { showErrorDialog.showDialog(null) }
                }
            }
        }
    }

    private fun popBack() {
        onPopBack()
    }
}
