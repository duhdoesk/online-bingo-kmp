package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.room.use_case.GetNotStartedRoomsUseCase
import domain.room.use_case.GetRunningRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import domain.theme.use_case.GetAllThemesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.state.JoinRoomUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val firebaseUser: FirebaseUser,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
    private val onCreateRoom: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val getNotStartedRoomsUseCase by inject<GetNotStartedRoomsUseCase>()
    private val getRunningRoomsUseCase by inject<GetRunningRoomsUseCase>()
    private val getAllThemesUseCase by inject<GetAllThemesUseCase>()
    private val joinRoomUseCase by inject<JoinRoomUseCase>()

    private val _query = MutableStateFlow("")

    private val _uiState = MutableStateFlow(JoinRoomUIState.INITIAL)
    val uiState: StateFlow<JoinRoomUIState> get() = _uiState.asStateFlow()

    private val _themes = getAllThemesUseCase()
    val themes = _themes

    val tapRoomDialogState = mutableDialogStateOf("")
    val errorDialogState = mutableDialogStateOf(null)

    fun uiEvent(event: JoinRoomUIEvent) {
        when (event) {
            JoinRoomUIEvent.CreateRoom -> createRoom()
            JoinRoomUIEvent.PopBack -> popBack()
            JoinRoomUIEvent.UiLoaded -> uiLoaded()

            is JoinRoomUIEvent.TapRoom -> tapRoom(event.roomName)

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
                getNotStartedRoomsUseCase(),
                getRunningRoomsUseCase(),
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

    private fun tapRoom(roomName: String) {
        tapRoomDialogState.showDialog(roomName)
    }

    private fun joinRoom(roomId: String, password: String?) {
        coroutineScope.launch {
            joinRoomUseCase
                .invoke(
                    roomId = roomId,
                    userId = firebaseUser.uid,
                    roomPassword = password,
                )
                .onSuccess { onJoinRoom(Configuration.PlayScreen(roomId)) }
                .onFailure { exception -> println("join fail with error: ${exception.message}") }
        }
    }

    private fun createRoom() =
        onCreateRoom()

    private fun popBack() =
        onPopBack()
}