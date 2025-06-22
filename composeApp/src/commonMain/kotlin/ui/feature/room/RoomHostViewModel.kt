package ui.feature.room

import com.arkivanov.decompose.ComponentContext
import domain.feature.user.model.User
import domain.feature.user.useCase.GetRoomPlayersUseCase
import domain.feature.user.useCase.GetUserByIdUseCase
import domain.room.useCase.GetRoomByIdUseCase
import domain.room.useCase.RaffleNextItemUseCase
import domain.room.useCase.UpdateRoomStateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.feature.room.event.RoomHostEvent
import ui.feature.room.state.RoomHostState
import ui.feature.room.state.auxiliar.BingoStyle
import ui.feature.room.state.auxiliar.RaffleButtonState
import util.componentCoroutineScope

class RoomHostViewModel(
    private val componentContext: ComponentContext,
    private val roomId: String,
    private val onPopBack: () -> Unit
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
    private val flowRoomByIdUseCase by inject<GetRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val observeUser: GetUserByIdUseCase by inject()

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
            RoomHostEvent.CleanErrors -> cleanErrors()
        }
    }

    /**
     * Loads and manages UI State
     */
    private fun uiLoaded() {
//        coroutineScope.launch {
//            combine(
//                flowRoomByIdUseCase(roomId = roomId),
//                getRoomPlayersUseCase(roomId = roomId)
//            ) { room, players ->
//
//                val bingoStyle = getBingoStyleUseCase(
//                    bingoType = room.type,
//                    themeId = room.themeId
//                ).getOrNull()
//
//                if (bingoStyle == null) {
//                    return@combine RoomHostState.INITIAL.copy(dataState = DataState.ERROR)
//                }
//
//                val buttonState =
//                    if (canRaffleNext(room.raffled.size, bingoStyle)) {
//                        RaffleButtonState.AVAILABLE
//                    } else {
//                        RaffleButtonState.DONE
//                    }
//
//                val host =
//                    observeUser(room.hostId).first().getOrNull()
//
//                RoomHostState(
//                    dataState = DataState.SUCCESS,
//                    bingoStyle = bingoStyle,
//                    bingoState = room.state,
//                    roomName = room.name,
//                    host = host,
//                    players = players.getOrNull() ?: emptyList(),
//                    winners = getWinners(ids = room.winnersIds, players = players.getOrNull() ?: emptyList()),
//                    maxWinners = room.maxWinners,
//                    raffledItems = room.raffled,
//                    raffleButtonState = buttonState,
//                    hostScreenError = null
//                )
//            }.collect { state ->
//                _screenState.update { state }
//            }
//        }
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
//            updateRoomStateUseCase(
//                roomId = roomId,
//                state = RoomState.FINISHED
//            ).onFailure { _screenState.update { it.copy(hostScreenError = HostScreenError.FINISH) } }
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
//            updateRoomStateUseCase(
//                roomId = roomId,
//                state = RoomState.RUNNING
//            ).onFailure { _screenState.update { it.copy(hostScreenError = HostScreenError.START) } }
        }
    }

    /**
     * Clears any error message in Screen State
     */
    private fun cleanErrors() {
        _screenState.update { it.copy(hostScreenError = null) }
    }
}
