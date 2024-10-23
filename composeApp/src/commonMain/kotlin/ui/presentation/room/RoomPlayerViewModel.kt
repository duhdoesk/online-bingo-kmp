package ui.presentation.room

import com.arkivanov.decompose.ComponentContext
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.room.model.BingoType
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.room.use_case.GetBingoStyleUseCase
import domain.user.model.User
import domain.user.use_case.GetRoomPlayersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.room.event.RoomPlayerEvent
import ui.presentation.room.state.RoomPlayerState
import ui.presentation.room.state.auxiliar.BingoStyle
import ui.presentation.room.state.auxiliar.CardState
import ui.presentation.room.state.auxiliar.DataState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class RoomPlayerViewModel(
    private val componentContext: ComponentContext,
    private val user: Flow<User?>,
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
    private val _screenState = MutableStateFlow(RoomPlayerState.INITIAL)
    val screenState = _screenState.asStateFlow()

    /**
     * Necessary Use Cases to build UI State
     */
    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val flowCardByRoomAndUserIDUseCase by inject<FlowCardByRoomAndUserIDUseCase>()
    private val getBingoStyleUseCase: GetBingoStyleUseCase by inject()

    /**
     * Action Use Cases
     */
    private val callBingoUseCase by inject<CallBingoUseCase>()
    private val setCardByRoomAndUserIDUseCase by inject<SetCardByRoomAndUserIDUseCase>()

    /**
     * Modal visibility holders
     */
    val errorDialogState = mutableDialogStateOf<String?>(null)
    val popBackDialogState = mutableDialogStateOf(null)

    /**
     * Delegates the handling of each UI Event
     */
    fun uiEvent(event: RoomPlayerEvent) {
        when (event) {
            RoomPlayerEvent.CallBingo -> callBingo()
            RoomPlayerEvent.GetNewCard -> updateCard()
            RoomPlayerEvent.PopBack -> popBackDialogState.showDialog(null)
            RoomPlayerEvent.ConfirmPopBack -> onPopBack()
            RoomPlayerEvent.UiLoaded -> uiLoaded()
        }
    }

    /**
     * Loads and manages UI State
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            // Informs the UI that data is being updated
            _screenState.update { it.copy(dataState = DataState.LOADING) }

            // Checks if user is null, informing UI of an error if true
            val userId = user.first()?.id
            if (userId == null) {
                _screenState.update { it.copy(dataState = DataState.ERROR) }
                return@launch
            }

            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                flowCardByRoomAndUserIDUseCase(roomId, userId),
            ) { room, players, card ->

                val cardState = checkCardState(
                    items = card?.characters,
                    bingoType = room.type,
                )

                val hasCalled = hasCalledBingo(room.winners)

                val canCall = canCallBingo(
                    cardState = cardState,
                    raffledCharacters = room.raffled,
                    hasCalledYet = hasCalled,
                )

                val bingoStyle = getBingoStyleUseCase(
                    bingoType = room.type,
                    themeId = room.themeId,
                ).getOrNull()

                if (bingoStyle == null)
                    return@combine RoomPlayerState.INITIAL.copy(dataState = DataState.ERROR)

                RoomPlayerState(
                    dataState = DataState.SUCCESS,
                    bingoStyle = bingoStyle,
                    bingoState = room.state,
                    roomName = room.name,
                    players = players,
                    winners = getWinners(ids = room.winners, players = players),
                    maxWinners = room.maxWinners,
                    raffledItems = room.raffled,
                    canCallBingo = canCall,
                    cardState = cardState,
                    userId = userId,
                )
            }.collect { state ->
                _screenState.update { state }
            }
        }
    }

    /**
     * Call bingo and add player to the list of winners
     */
    private fun callBingo() {
        coroutineScope.launch {
            callBingoUseCase(
                roomId = roomId,
                userId = screenState.value.userId.orEmpty(),
            ).onFailure { exception -> errorDialogState.showDialog(exception.message) }
        }
    }

    /**
     * Checks if the player fills all the requisites to call bingo
     */
    private fun canCallBingo(
        cardState: CardState,
        raffledCharacters: List<String>,
        hasCalledYet: Boolean,
    ): Boolean {
        if (hasCalledYet) return false

        when (cardState) {
            is CardState.Success -> {
                if (cardState.items.isEmpty()) return false
                cardState.items.forEach { character -> if (character !in raffledCharacters) return false }
            }

            else -> return false
        }

        return true
    }

    private fun checkCardState(
        items: List<String>?,
        bingoType: BingoType,
    ): CardState {
        // Returns Error if there is no list of items
        if (items == null) return CardState.Error

        // Returns Error if there is card size is different than requested
        when (bingoType) {
            BingoType.CLASSIC -> if (items.size != 24) return CardState.Error
            BingoType.THEMED -> if (items.size != 9) return CardState.Error
        }

        // Returns Success after passing the checks
        return CardState.Success(items = items)
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
     * Checks if player has called bingo already
     */
    private fun hasCalledBingo(winners: List<String>): Boolean {
        if (screenState.value.userId == null) return false
        return winners.contains(screenState.value.userId)
    }

    /**
     * Get a new random card
     */
    private fun updateCard() {
        coroutineScope.launch {
            val newCard = getNewCard()

            setCardByRoomAndUserIDUseCase(
                roomId = roomId,
                userId = screenState.value.userId.orEmpty(),
                charactersIDs = newCard,
            ).onFailure { exception -> errorDialogState.showDialog(exception.message) }
        }
    }

    private fun getNewCard(): List<String> {
        when (val style = screenState.value.bingoStyle) {
            is BingoStyle.Classic -> {
                val newCard = mutableListOf<Int>()
                newCard.addAll((1..15).shuffled().subList(0, 5))
                newCard.addAll((16..30).shuffled().subList(0, 5))
                newCard.addAll((31..45).shuffled().subList(0, 4))
                newCard.addAll((46..60).shuffled().subList(0, 5))
                newCard.addAll((61..75).shuffled().subList(0, 5))

                return newCard.map { it.toString() }
            }

            is BingoStyle.Themed -> {
                val items = style.characters.map { it.id }
                return items.shuffled().subList(0, 9)
            }
        }
    }
}