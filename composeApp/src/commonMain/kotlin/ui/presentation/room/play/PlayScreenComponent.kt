package ui.presentation.room.play

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.character.model.Character
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
): ComponentContext by componentContext, KoinComponent {

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
    private val canCallBingo = MutableStateFlow(false)

    /**
     * Action Use Cases
     */
    //todo()

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
                canCallBingo,
            ) { room, players, characters, theme, canCallBingo ->

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

                PlayScreenUIState(
                    loading = false,
                    players = players,
                    theme = theme,
                    raffledCharacters = raffledCharacters.reversed(),
                    maxWinners = room.maxWinners,
                    winners = winnersList,
                    roomName = room.name,
                    bingoType = room.type,
                    bingoState = room.state,
                    canCallBingo = canCallBingo,
                    calledBingo = hasCalledBingo,
                    myCard = null, //todo(): fix
                )
            }
                .collect { state ->
                    _uiState.update { state }
                }
        }
    }

    private fun getNewCard() {
        //todo()
    }

    private fun callBingo() {
        //todo()
    }

    private fun popBack() {
        onPopBack()
    }
}