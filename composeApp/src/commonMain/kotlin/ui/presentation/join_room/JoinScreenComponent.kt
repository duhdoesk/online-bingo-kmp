package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.use_case.GetNotStartedRoomsUseCase
import domain.room.use_case.GetRoomByIdUseCase
import domain.room.use_case.GetRunningRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import domain.theme.use_case.GetAllThemesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.join_room_invalid_password
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.navigation.Configuration
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.state.JoinRoomUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val firebaseUser: FirebaseUser,
    private val bingoType: BingoType,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
    private val onJoinRoomAsHost: (configuration: Configuration) -> Unit,
    private val onCreateRoom: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val getNotStartedRoomsUseCase by inject<GetNotStartedRoomsUseCase>()
    private val getRunningRoomsUseCase by inject<GetRunningRoomsUseCase>()
    private val getAllThemesUseCase by inject<GetAllThemesUseCase>()
    private val joinRoomUseCase by inject<JoinRoomUseCase>()
    private val getRoomByIdUseCase by inject<GetRoomByIdUseCase>()

    private val _query = MutableStateFlow("")

    private val _uiState = MutableStateFlow(JoinRoomUIState.INITIAL)
    val uiState: StateFlow<JoinRoomUIState> get() = _uiState.asStateFlow()

    private val _themes = getAllThemesUseCase()
    val themes = _themes

    val tapRoomDialogState = mutableDialogStateOf<BingoRoom?>(null)

    @OptIn(ExperimentalResourceApi::class)
    val errorDialogState = mutableDialogStateOf<StringResource?>(null)

    fun uiEvent(event: JoinRoomUIEvent) {
        when (event) {
            JoinRoomUIEvent.CreateRoom -> createRoom()
            JoinRoomUIEvent.PopBack -> popBack()
            JoinRoomUIEvent.UiLoaded -> uiLoaded()

            is JoinRoomUIEvent.TapRoom -> tapRoom(event.room)

            is JoinRoomUIEvent.JoinRoom -> joinRoom(
                roomId = event.roomId,
                password = event.roomPassword,
            )

            is JoinRoomUIEvent.QueryTyping -> updateQuery(event.query)
        }
    }

    private fun uiLoaded() {
        coroutineScope.launch {
            combine(
                getNotStartedRoomsUseCase(bingoType),
                getRunningRoomsUseCase(bingoType),
                _query,
            ) { notStarted, running, query ->

                if (query.isEmpty()) {
                    JoinRoomUIState(
                        loading = false,
                        notStartedRooms = notStarted,
                        runningRooms = running,
                        query = query
                    )
                } else {
                    val filteredNotStarted =
                        notStarted.filter { it.name.lowercase().contains(query.lowercase()) }
                    val filteredRunning =
                        running.filter { it.name.lowercase().contains(query.lowercase()) }

                    JoinRoomUIState(
                        loading = false,
                        notStartedRooms = filteredNotStarted,
                        runningRooms = filteredRunning,
                        query = query
                    )
                }
            }.collect { state -> _uiState.update { state } }
        }
    }

    private fun updateQuery(query: String) {
        _query.update { query }
    }

    private fun tapRoom(room: BingoRoom) {
        tapRoomDialogState.showDialog(room)
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun joinRoom(roomId: String, password: String?) {
        coroutineScope.launch {
            getRoomByIdUseCase(roomId)
                .onSuccess { room ->
                    if (room.hostId == firebaseUser.uid) {
                        onJoinRoomAsHost(Configuration.HostScreen(roomId))
                        return@launch
                    }
                }
                .onFailure { return@launch }

            joinRoomUseCase
                .invoke(
                    roomId = roomId,
                    userId = firebaseUser.uid,
                    roomPassword = password,
                )
                .onSuccess { onJoinRoom(Configuration.PlayScreen(roomId)) }
                .onFailure { exception ->

                    if (exception.message == "Incorrect Password") {
                        errorDialogState.showDialog(Res.string.join_room_invalid_password)
                        return@launch
                    }

                    errorDialogState.showDialog(Res.string.unmapped_error)
                }
        }
    }

    private fun createRoom() =
        onCreateRoom()

    private fun popBack() =
        onPopBack()
}