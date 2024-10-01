package ui.presentation.room.classic.host

import com.arkivanov.decompose.ComponentContext
import domain.room.model.RoomState
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.RaffleNextCharacterUseCase
import domain.room.use_case.UpdateRoomStateUseCase
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
import ui.presentation.room.classic.host.event.ClassicHostScreenUIEvent
import ui.presentation.room.classic.host.state.ClassicHostScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

val NUMBERS = (1..75).toList()

class ClassicHostScreenComponent(
    componentContext: ComponentContext,
    private val roomId: String,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single Coroutine Scope
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * State building Use Cases
     */
    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val canRaffleNextNumber = MutableStateFlow(true)

    /**
     * Action Use Cases
     */
    private val updateRoomStateUseCase by inject<UpdateRoomStateUseCase>()
    private val raffleNextCharacterUseCase by inject<RaffleNextCharacterUseCase>()

    /**
     * Confirmation Dialogs
     */
    val finishRaffleDialogState = mutableDialogStateOf(null)
    val popBackDialogState = mutableDialogStateOf(null)
    val showGenericErrorDialog = mutableDialogStateOf(null)

    /**
     * UI State Holder
     */
    private val _uiState = MutableStateFlow(ClassicHostScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    /**
     * Function to delegate UI Events
     */
    fun uiEvent(event: ClassicHostScreenUIEvent) {
        when (event) {
            ClassicHostScreenUIEvent.ConfirmFinishRaffle -> finishRaffle()
            ClassicHostScreenUIEvent.ConfirmPopBack -> popBack()
            ClassicHostScreenUIEvent.FinishRaffle -> finishRaffleDialogState.showDialog(null)
            ClassicHostScreenUIEvent.PopBack -> popBackDialogState.showDialog(null)
            ClassicHostScreenUIEvent.RaffleNextNumber -> raffleNextNumber()
            ClassicHostScreenUIEvent.StartRaffle -> startRaffle()
            ClassicHostScreenUIEvent.UiLoaded -> uiLoaded()
        }
    }

    /**
     * Functions to handle each UI Event
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                canRaffleNextNumber,
            ) { room, players, raffle ->

                val winnersList = mutableListOf<User>()
                room.winners.forEach { winnerId ->
                    players.find { it.id == winnerId }?.run { winnersList.add(this) }
                }

                val canRaffleNext =
                    if (room.drawnCharactersIds.size == NUMBERS.size) false else raffle

                ClassicHostScreenUIState(
                    loading = false,
                    players = players,
                    numbers = NUMBERS,
                    raffledNumbers = room.drawnCharactersIds.map { it.toInt() },
                    maxWinners = room.maxWinners,
                    winners = winnersList,
                    roomName = room.name,
                    bingoState = room.state,
                    canRaffleNextNumber = canRaffleNext,
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

    private fun raffleNextNumber() {
        coroutineScope.launch {
            canRaffleNextNumber.update { false }

            val nextNumber = uiState.value.run {
                numbers
                    .filterNot { it in raffledNumbers }
                    .shuffled()
                    .first()
            }

            raffleNextCharacterUseCase(roomId = roomId, characterId = nextNumber.toString())
                .onFailure { showGenericErrorDialog.showDialog(null) }
                .onSuccess {
                    delay(1000)
                    canRaffleNextNumber.update { true }
                }
        }
    }

    private fun popBack() {
        onPopBack()
    }
}