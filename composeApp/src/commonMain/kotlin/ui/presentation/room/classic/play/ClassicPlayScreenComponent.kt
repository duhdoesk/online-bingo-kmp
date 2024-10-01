package ui.presentation.room.classic.play

import com.arkivanov.decompose.ComponentContext
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.room.model.RoomState
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.FlowRoomByIdUseCase
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
import ui.presentation.room.classic.host.NUMBERS
import ui.presentation.room.classic.play.event.ClassicPlayScreenUIEvent
import ui.presentation.room.classic.play.state.ClassicPlayScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class ClassicPlayScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    private val roomId: String,
    private val onPopBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Single coroutine scope to handle each suspend operation
     */
    private val coroutineScope = componentCoroutineScope()

    /**
     * Necessary flows to build UI State
     */
    private val flowRoomByIdUseCase by inject<FlowRoomByIdUseCase>()
    private val getRoomPlayersUseCase by inject<GetRoomPlayersUseCase>()
    private val flowCardByRoomAndUserIDUseCase by inject<FlowCardByRoomAndUserIDUseCase>()

    /**
     * Action Use Cases
     */
    private val setCardByRoomAndUserIDUseCase by inject<SetCardByRoomAndUserIDUseCase>()
    private val callBingoUseCase by inject<CallBingoUseCase>()

    /**
     * UI State
     */
    private val _uiState = MutableStateFlow(ClassicPlayScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    /**
     * Modal visibility holders
     */
    val popBackDialogState = mutableDialogStateOf(null)
    val showErrorDialog = mutableDialogStateOf(null)

    /**
     * Function to delegate the handling of user interactions
     */
    fun uiEvent(event: ClassicPlayScreenUIEvent) {
        when (event) {
            ClassicPlayScreenUIEvent.CallBingo -> callBingo()
            ClassicPlayScreenUIEvent.ConfirmPopBack -> popBack()
            ClassicPlayScreenUIEvent.GetNewCard -> getNewCard()
            ClassicPlayScreenUIEvent.PopBack -> popBackDialogState.showDialog(null)
            ClassicPlayScreenUIEvent.UiLoaded -> uiLoaded()
        }
    }

    /**
     * Functions to handle each interaction
     */
    private fun uiLoaded() {
        coroutineScope.launch {
            val userId = user.first()?.id.orEmpty()

            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                flowCardByRoomAndUserIDUseCase(roomId, userId),
            ) { room, players, card ->

                val winnersList = mutableListOf<User>()
                room.winners.forEach { winnerId ->
                    players.find { it.id == winnerId }?.run { winnersList.add(this) }
                }

                val hasCalledBingo =
                    room.winners.contains(userId)

                val canCall = canCallBingo(
                    card = card?.characters,
                    raffledCharacters = room.drawnCharactersIds,
                    hasCalledYet = hasCalledBingo,
                )

                val myCard = card?.characters?.map { it.toInt() }.orEmpty()

                ClassicPlayScreenUIState(
                    loading = false,
                    players = players,
                    numbers = NUMBERS,
                    raffledNumbers = room.drawnCharactersIds.map { it.toInt() },
                    maxWinners = room.maxWinners,
                    winners = winnersList,
                    roomName = room.name,
                    bingoState = room.state,
                    canCallBingo = canCall,
                    calledBingo = hasCalledBingo,
                    myCard = myCard,
                )
            }
                .collect { state ->
                    _uiState.update { state }
                    if (state.myCard.isEmpty() && state.bingoState == RoomState.NOT_STARTED) getNewCard()
                }
        }
    }

    private fun getNewCard() {
        coroutineScope.launch {
            user.collect { collectedUser ->
                if (collectedUser != null) {
                    val newCard = mutableListOf<Int>()

                    newCard.addAll((1..15).shuffled().subList(0, 5))
                    newCard.addAll((16..30).shuffled().subList(0, 5))
                    newCard.addAll((31..45).shuffled().subList(0, 4))
                    newCard.addAll((46..60).shuffled().subList(0, 5))
                    newCard.addAll((61..75).shuffled().subList(0, 5))

                    setCardByRoomAndUserIDUseCase(
                        roomId = roomId,
                        userId = collectedUser.id,
                        charactersIDs = newCard.map { it.toString() },
                    )
                        .onFailure { showErrorDialog.showDialog(null) }
                }
            }
        }
    }

    private fun callBingo() {
        coroutineScope.launch {
            user.collect { collectedUser ->
                if (collectedUser != null) {
                    callBingoUseCase(
                        roomId = roomId,
                        userId = collectedUser.id,
                    )
                        .onFailure { showErrorDialog.showDialog(null) }
                }
            }
        }
    }

    private fun popBack() {
        onPopBack()
    }

    private fun canCallBingo(
        card: List<String>?,
        raffledCharacters: List<String>,
        hasCalledYet: Boolean,
    ): Boolean {
        if (card == null) return false
        if (hasCalledYet) return false
        if (card.isEmpty()) return false
        card.forEach { character -> if (character !in raffledCharacters) return false }
        return true
    }
}

