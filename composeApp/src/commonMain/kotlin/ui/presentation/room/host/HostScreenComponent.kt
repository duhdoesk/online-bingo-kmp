package ui.presentation.room.host

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
import ui.presentation.room.host.event.HostScreenUIEvent
import ui.presentation.room.host.state.HostScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class HostScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    val roomId: String,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val getRoomCharactersUseCase by inject<GetRoomCharactersUseCase>()
    private val getRoomThemeUseCase by inject<GetRoomThemeUseCase>()
    private val canRaffleNextCharacter = MutableStateFlow(true)

    private val updateRoomStateUseCase by inject<UpdateRoomStateUseCase>()
    private val raffleNextCharacterUseCase by inject<RaffleNextCharacterUseCase>()

    private val _uiState = MutableStateFlow(HostScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    val finishRaffleDialogState = mutableDialogStateOf(null)
    val popBackDialogState = mutableDialogStateOf(null)

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
                .onFailure { } //todo(): show error dialog
        }
    }

    private fun finishRaffle() {
        coroutineScope.launch {
            updateRoomStateUseCase(
                roomId = roomId,
                state = RoomState.FINISHED
            )
                .onFailure { } //todo(): show error dialog
                .onSuccess { } //todo(): show dialog informing that the bingo is over
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
                .onFailure { } //todo(): show error dialog
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