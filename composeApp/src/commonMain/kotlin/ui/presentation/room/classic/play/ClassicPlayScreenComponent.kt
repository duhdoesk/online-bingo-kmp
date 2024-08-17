package ui.presentation.room.classic.play

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.character.model.Character
import domain.room.model.RoomState
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.user.model.User
import domain.user.use_case.GetRoomPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val firebaseUser: FirebaseUser,
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
     * Dialog called to confirm that the user wants to leave the room
     */
    val popBackDialogState = mutableDialogStateOf(null)

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
            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                flowCardByRoomAndUserIDUseCase(roomId, firebaseUser.uid),
            ) { room, players, card ->

                val winnersList = mutableListOf<User>()
                room.winners.forEach { winnerId ->
                    players.find { it.id == winnerId }?.run { winnersList.add(this) }
                }

                val hasCalledBingo =
                    room.winners.contains(firebaseUser.uid)

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
                    if (state.myCard.isEmpty()) getNewCard()
                }
        }
    }

    private fun getNewCard() {
        coroutineScope.launch {
            val newCard = uiState.value.numbers.shuffled().subList(0, 24).map { it.toString() }

            setCardByRoomAndUserIDUseCase(
                roomId = roomId,
                userId = firebaseUser.uid,
                charactersIDs = newCard,
            )
                .onFailure { exception -> println(exception) } //todo(): display error message
        }
    }

    private fun callBingo() {
        coroutineScope.launch {
            callBingoUseCase(
                roomId = roomId,
                userId = firebaseUser.uid,
            )
                .onFailure { exception -> println(exception) } //todo(): display error message
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

