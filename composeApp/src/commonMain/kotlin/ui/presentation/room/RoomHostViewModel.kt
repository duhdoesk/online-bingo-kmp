package ui.presentation.room

import com.arkivanov.decompose.ComponentContext
import domain.room.model.RoomState
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.GetBingoStyleUseCase
import domain.room.use_case.RaffleNextItemUseCase
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
import ui.presentation.room.event.RoomHostEvent
import ui.presentation.room.state.RoomHostState
import ui.presentation.room.state.auxiliar.BingoStyle
import ui.presentation.room.state.auxiliar.DataState
import ui.presentation.room.state.auxiliar.RaffleButtonState
import util.componentCoroutineScope

class RoomHostViewModel(
    private val componentContext: ComponentContext,
    private val roomId: String,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single coroutine scope to handle each suspend operation
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Screen State private holder and public exposer
     */
    private val _screenState = MutableStateFlow(RoomHostState.INITIAL)
    val screenState = _screenState.asStateFlow()

    /**
     * Necessary Use Cases to build UI State
     */
    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val getBingoStyleUseCase: GetBingoStyleUseCase by inject()

    /**
     * Action Use Cases
     */
    private val raffleNextItemUseCase by inject<RaffleNextItemUseCase>()
    private val updateRoomStateUseCase by inject<UpdateRoomStateUseCase>()

    /**
     * Delegates the handling of each UI Event
     */
    fun uiEvent(event: RoomHostEvent) {
        when (event) {
            RoomHostEvent.FinishRaffle -> finishRaffle()
            RoomHostEvent.RaffleNextItem -> raffleNextItem()
            RoomHostEvent.StartRaffle -> startRaffle()
            RoomHostEvent.UiLoaded -> uiLoaded()
            RoomHostEvent.PopBack -> popBack()
        }
    }

    /**
     * Loads and manages UI State
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            combine(
                flowRoomByIdUseCase(roomId = roomId),
                getRoomPlayersUseCase(roomId = roomId),
            ) { room, players ->

                val bingoStyle = getBingoStyleUseCase(
                    bingoType = room.type,
                    themeId = room.themeId,
                ).getOrNull()

                if (bingoStyle == null)
                    return@combine RoomHostState.INITIAL.copy(dataState = DataState.ERROR)

                val buttonState =
                    if (canRaffleNext(room.raffled.size, bingoStyle)) RaffleButtonState.AVAILABLE
                    else RaffleButtonState.DONE

                RoomHostState(
                    dataState = DataState.SUCCESS,
                    bingoStyle = bingoStyle,
                    bingoState = room.state,
                    roomName = room.name,
                    players = players,
                    winners = getWinners(ids = room.winners, players = players),
                    maxWinners = room.maxWinners,
                    raffledItems = room.raffled,
                    raffleButtonState = buttonState,
                )
            }.collect { state ->
                _screenState.update { state }
            }
        }
    }

    /**
     * Calculates if host can raffle next item
     */
    private fun canRaffleNext(raffledSize: Int, style: BingoStyle): Boolean {
        return when (style) {
            is BingoStyle.Classic -> {
                raffledSize < 75
            }

            is BingoStyle.Themed -> {
                raffledSize < style.characters.size
            }
        }
    }

    /**
     * Finishes the raffle, changing the room state from RUNNING to FINISHED
     */
    private fun finishRaffle() {
        coroutineScope.launch {
            updateRoomStateUseCase(
                roomId = roomId,
                state = RoomState.FINISHED
            )
                .onFailure { } //todo(): inform UI
        }
    }

    /**
     * Returns the list of winners
     */
    private fun getWinners(ids: List<String>, players: List<User>): List<User> {
        val winners = mutableListOf<User>()
        ids.forEach { userId ->
            players.find { it.id == userId }?.run { winners.add(this) }
        }
        return winners
    }

    /**
     * Pops back to last or previous screen
     */
    private fun popBack() {
        onPopBack()
    }

    /**
     * Raffles next item
     */
    private fun raffleNextItem() {
        coroutineScope.launch {

            _screenState.update { it.copy(raffleButtonState = RaffleButtonState.SUSPEND) }

            val style = screenState.value.bingoStyle
            val raffled = screenState.value.raffledItems

            val notRaffled = when (style) {
                is BingoStyle.Classic -> {
                    val items = (1..75).toList().map { it.toString() }
                    items.filterNot { it in raffled }
                }

                is BingoStyle.Themed -> {
                    val items = style.characters.map { it.id }
                    items.filterNot { it in raffled }
                }
            }

            // If UI fails to update button state and it still enable when having no item left
            // to be raffled, the function should stop here, preventing crashes by returning@launch
            if (notRaffled.isEmpty()) {
                _screenState.update { it.copy(raffleButtonState = RaffleButtonState.DONE) }
                return@launch
            }

            // Delays the operation to keep the button suspended for 400ms (ux rule), then raffles
            // the next item and sends info to API
            delay(400)
            val nextItem = notRaffled.shuffled().first()
            raffleNextItemUseCase(roomId = roomId, item = nextItem)
        }
    }

    /**
     * Starts the raffle, changing the room state from NOT_STARTED to RUNNING
     */
    private fun startRaffle() {
        coroutineScope.launch {
            updateRoomStateUseCase(
                roomId = roomId,
                state = RoomState.RUNNING
            )
                .onFailure { } //todo(): inform UI
        }
    }
}