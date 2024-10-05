package ui.presentation.room.themed.play

import com.arkivanov.decompose.ComponentContext
import domain.card.use_case.FlowCardByRoomAndUserIDUseCase
import domain.card.use_case.SetCardByRoomAndUserIDUseCase
import domain.character.model.Character
import domain.room.use_case.CallBingoUseCase
import domain.room.use_case.FlowRoomByIdUseCase
import domain.theme.model.BingoTheme
import domain.theme.use_case.GetRoomThemeUseCase
import domain.theme.use_case.GetThemeCharactersUseCase
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
import ui.presentation.room.themed.play.event.PlayScreenUIEvent
import ui.presentation.room.themed.play.state.CardState
import ui.presentation.room.themed.play.state.PlayScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class PlayScreenComponent(
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
    private val getThemeCharactersUseCase by inject<GetThemeCharactersUseCase>()
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
     * Constants
     */
    private var characters = emptyList<Character>()
    private var theme: BingoTheme? = null
    private var userId = ""

    /**
     * Dialog called to confirm that the user wants to leave the room
     */
    val popBackDialogState = mutableDialogStateOf(null)
    val showErrorDialog = mutableDialogStateOf(null)

    /**
     * Function to delegate the handling of user interactions
     */
    fun uiEvent(uiEvent: PlayScreenUIEvent) {
        when (uiEvent) {
            PlayScreenUIEvent.CallBingo -> callBingo()
            PlayScreenUIEvent.ConfirmPopBack -> popBack()
            PlayScreenUIEvent.GetNewCard -> updateCard()
            PlayScreenUIEvent.PopBack -> popBackDialogState.showDialog(null)
            PlayScreenUIEvent.UiLoaded -> uiLoaded()
        }
    }

    /**
     * Functions to handle each interaction
     */
    private fun uiLoaded() {
        coroutineScope.launch {

            theme = getRoomThemeUseCase(roomId).getOrNull()
            characters = getThemeCharactersUseCase(theme?.id.orEmpty()).getOrNull() ?: emptyList()
            userId = user.first()?.id.orEmpty()

            if (theme == null || characters.isEmpty()) {
                //todo(): handle backend errors
            }

            combine(
                flowRoomByIdUseCase(roomId),
                getRoomPlayersUseCase(roomId),
                flowCardByRoomAndUserIDUseCase(roomId, userId),
            ) { room, players, card ->

                val raffledCharacters =
                    checkRaffledCharacters(room.drawnCharactersIds)

                val winnersList =
                    getWinners(winnersIds = room.winners, players = players)

                val hasCalledBingo =
                    room.winners.contains(userId)

                val cardState =
                    checkCardState(card?.characters)

                if (cardState is CardState.Error) { updateCard() }

                val canCall =
                    canCallBingo(cardState, raffledCharacters, hasCalledBingo)

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
                    myCard = cardState,
                )
            }.collect { state -> _uiState.update { state } }
        }
    }

    private fun updateCard() {
        coroutineScope.launch {
            val newCard = getNewCard().map { it.id }

            setCardByRoomAndUserIDUseCase(
                roomId = roomId,
                userId = userId,
                charactersIDs = newCard,
            )
                .onFailure { showErrorDialog.showDialog(null) }
        }
    }

    private fun getNewCard(): List<Character> {
        return characters.shuffled().subList(0, 9)
    }

    private fun callBingo() {
        coroutineScope.launch {
            callBingoUseCase(
                roomId = roomId,
                userId = userId,
            )
                .onFailure { showErrorDialog.showDialog(null) }
        }
    }

    private fun popBack() {
        onPopBack()
    }

    private fun canCallBingo(
        cardState: CardState,
        raffledCharacters: List<Character>,
        hasCalledYet: Boolean,
    ): Boolean {
        if (hasCalledYet) return false

        when (cardState) {
            is CardState.Success -> {
                if (cardState.characters.isEmpty()) return false
                cardState.characters.forEach { character -> if (character !in raffledCharacters) return false }
            }

            else -> return false
        }

        return true
    }

    private fun checkRaffledCharacters(raffledIds: List<String>): List<Character> {
        val charactersList = mutableListOf<Character>()

        raffledIds.forEach { characterId ->
            characters.find { it.id == characterId }?.run { charactersList.add(this) }
        }

        return charactersList
    }

    private fun getWinners(winnersIds: List<String>, players: List<User>): List<User> {
        val winners = mutableListOf<User>()

        winnersIds.forEach { winnerId ->
            players.find { it.id == winnerId }?.run { winners.add(this) }
        }

        return winners
    }

    private fun checkCardState(charactersIds: List<String>?): CardState {
        if (charactersIds.isNullOrEmpty()) return CardState.Error
        if (charactersIds.size < 9) return CardState.Error

        val charactersList = charactersIds.let { characterId ->
            characters.filter { it.id in characterId }
        }
        return CardState.Success(charactersList)
    }
}