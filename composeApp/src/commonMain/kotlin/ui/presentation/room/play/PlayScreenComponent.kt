package ui.presentation.room.play

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.character.model.Character
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.theme.use_case.GetRoomCharactersUseCase
import domain.theme.use_case.GetRoomThemeUseCase
import domain.user.model.User
import domain.user.use_case.GetRoomPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.room.play.event.PlayScreenUIEvent
import ui.presentation.room.play.state.PlayScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class PlayScreenComponent(
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
    private val getRoomCharactersUseCase by inject<GetRoomCharactersUseCase>()
    private val getRoomThemeUseCase by inject<GetRoomThemeUseCase>()
    private val flowCardByRoomAndUserIDUseCase by inject<FlowCardByRoomAndUserIDUseCase>()

    /**
     * Action Use Cases
     */
    private val setCardByRoomAndUserIDUseCase by inject<SetCardByRoomAndUserIDUseCase>()
    private val callBingoUseCase by inject<CallBingoUseCase>()

    /**
     * UI State
     */
    private val _uiState = MutableStateFlow(PlayScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    /**
     * Dialog called to confirm that the user wants to leave the room
     */
    val popBackDialogState = mutableDialogStateOf(null)

    /**
     * Function to delegate the handling of user interactions
     */
    fun uiEvent(uiEvent: PlayScreenUIEvent) {
        when (uiEvent) {
            PlayScreenUIEvent.CallBingo -> callBingo()
            PlayScreenUIEvent.ConfirmPopBack -> popBack()
            PlayScreenUIEvent.GetNewCard -> getNewCard()
            PlayScreenUIEvent.PopBack -> popBackDialogState.showDialog(null)
            PlayScreenUIEvent.UiLoaded -> uiLoaded()
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
                getRoomCharactersUseCase(roomId),
                getRoomThemeUseCase(roomId),
                flowCardByRoomAndUserIDUseCase(roomId, firebaseUser.uid),
            ) { room, players, characters, theme, card ->

                val raffledCharacters = mutableListOf<Character>()
                room.drawnCharactersIds.forEach { characterId ->
                    characters.find { it.id == characterId }?.run { raffledCharacters.add(this) }
                }

                val winnersList = mutableListOf<User>()
                room.winners.forEach { winnerId ->
                    players.find { it.id == winnerId }?.run { winnersList.add(this) }
                }

                val hasCalledBingo =
                    room.winners.contains(firebaseUser.uid)

                val cardCharacters = mutableListOf<Character>()

                card?.characters?.forEach { id ->
                    characters.find { it.id == id }?.run { cardCharacters.add(this) }
                }

                val canCall = canCallBingo(
                    card = cardCharacters,
                    raffledCharacters = raffledCharacters,
                    hasCalledYet = hasCalledBingo,
                )

                PlayScreenUIState(
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
                    canCallBingo = canCall,
                    calledBingo = hasCalledBingo,
                    myCard = cardCharacters,
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
            val newCard = uiState.value.characters.shuffled().subList(0, 9).map { it.id }

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
        card: List<Character>,
        raffledCharacters: List<Character>,
        hasCalledYet: Boolean,
    ): Boolean {
        if (hasCalledYet) return false
        if (card.isEmpty()) return false
        card.forEach { character -> if (character !in raffledCharacters) return false }
        return true
    }
}