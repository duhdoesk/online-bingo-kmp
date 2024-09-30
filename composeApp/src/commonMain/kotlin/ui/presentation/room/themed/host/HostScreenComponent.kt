package ui.presentation.room.themed.host

import com.arkivanov.decompose.ComponentContext
import domain.character.model.Character
import domain.room.model.RoomState
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.RaffleNextCharacterUseCase
import domain.room.use_case.UpdateRoomStateUseCase
import domain.theme.use_case.GetRoomCharactersUseCase
import domain.theme.use_case.GetRoomThemeUseCase
import domain.user.model.User
import domain.user.use_case.GetRoomPlayersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.room.themed.host.event.HostScreenUIEvent
import ui.presentation.room.themed.host.state.HostScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class HostScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    val roomId: String,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Coroutine Scope
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Use Cases
     */
    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val getRoomCharactersUseCase by inject<GetRoomCharactersUseCase>()
    private val getRoomThemeUseCase by inject<GetRoomThemeUseCase>()
    private val updateRoomStateUseCase by inject<UpdateRoomStateUseCase>()
    private val raffleNextCharacterUseCase by inject<RaffleNextCharacterUseCase>()

    /**
     * UI State holders
     */
    private val _uiState = MutableStateFlow(HostScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()
    private val canRaffleNextCharacter = MutableStateFlow(true)

    /**
     * Modal visibility holders
     */
    val finishRaffleDialogState = mutableDialogStateOf(null)
    val popBackDialogState = mutableDialogStateOf(null)
    val showGenericErrorDialog = mutableDialogStateOf(null)

    /**
     * UI Event Delegate function
     */
    fun uiEvent(event: HostScreenUIEvent) {
        when (event) {
            HostScreenUIEvent.FinishRaffle -> finishRaffleDialogState.showDialog(null)
            HostScreenUIEvent.ConfirmFinishRaffle -> finishRaffle()
            HostScreenUIEvent.RaffleNextCharacter -> raffleNextCharacter()
            HostScreenUIEvent.StartRaffle -> startRaffle()
            HostScreenUIEvent.UiLoaded -> onUiLoaded()
            HostScreenUIEvent.PopBack -> popBackDialogState.showDialog(null)
            HostScreenUIEvent.ConfirmPopBack -> popBack()
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun onUiLoaded() {
        coroutineScope.launch {
            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                getRoomCharactersUseCase(roomId),
                getRoomThemeUseCase(roomId),
                canRaffleNextCharacter,
            ) { room, players, characters, theme, canRaffle ->

                val raffledCharacters = mutableListOf<Character>()
                room.drawnCharactersIds.forEach { characterId ->
                    characters.find { it.id == characterId }?.run { raffledCharacters.add(this) }
                }

                val winnersList = mutableListOf<User>()
                room.winners.forEach { winnerId ->
                    players.find { it.id == winnerId }?.run { winnersList.add(this) }
                }

                val canRaffleNext =
                    if (room.drawnCharactersIds.size == characters.size) false else canRaffle

                HostScreenUIState(
                    loading = false,
                    players = players,
                    theme = theme,
                    characters = characters,
                    raffledCharacters = raffledCharacters.reversed(),
                    maxWinners = room.maxWinners,
                    winners = winnersList,
                    roomName = room.name,
                    bingoType = room.type,
                    bingoState = room.state,
                    canRaffleNextCharacter = canRaffleNext,
                )
            }
                .collect { state ->
                    _uiState.update { state }
                }
        }
    }

    private fun startRaffle() {
        coroutineScope.launch {
            updateRoomStateUseCase(
                roomId = roomId,
                state = RoomState.RUNNING
            )
                .onFailure { showGenericErrorDialog.showDialog(null) }
        }
    }

    private fun finishRaffle() {
        coroutineScope.launch {
            updateRoomStateUseCase(
                roomId = roomId,
                state = RoomState.FINISHED
            )
                .onFailure { showGenericErrorDialog.showDialog(null) }
        }
    }

    private fun raffleNextCharacter() {
        coroutineScope.launch {
            canRaffleNextCharacter.update { false }

            val nextCharacter = uiState
                .value
                .characters
                .map { character -> character.id }
                .filterNot { id -> id in uiState.value.raffledCharacters.map { character -> character.id } }
                .shuffled()
                .first()

            raffleNextCharacterUseCase(roomId = roomId, characterId = nextCharacter)
                .onFailure { showGenericErrorDialog.showDialog(null) }
                .onSuccess {
                    delay(1000)
                    canRaffleNextCharacter.update { true }
                }
        }
    }

    private fun popBack() {
        onPopBack()
    }
}