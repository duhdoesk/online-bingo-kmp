package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext
import domain.room.use_case.GetRoomsUseCase
import domain.room.use_case.JoinRoomUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import ui.presentation.join_room.event.JoinRoomUIEvent
import util.componentCoroutineScope

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
    private val onCreateRoom: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val getRoomsUseCase by inject<GetRoomsUseCase>()
    private val joinRoomUseCase by inject<JoinRoomUseCase>()

    private val _roomsList = getRoomsUseCase.invoke()
    val roomsList = _roomsList
        .stateIn(
            componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
        coroutineScope.launch {
            joinRoomUseCase.invoke(
                roomId = "05Fq1gbeDkAA0SYU3Zjt",
                userId = "teste",
                roomPassword = "password"
            )
                .onSuccess { println("join success") }
                .onFailure { exception -> println("join fail with error: ${exception.message}") }
        }
    }

    fun uiEvent(event: JoinRoomUIEvent) {
        when (event) {
            JoinRoomUIEvent.CreateRoom -> createRoom()
            is JoinRoomUIEvent.JoinRoom -> joinRoom(event.roomId)
            JoinRoomUIEvent.PopBack -> popBack()
        }
    }

    private fun joinRoom(roomId: String) =
        onJoinRoom(Configuration.PlayScreen(roomId))

    private fun createRoom() =
        onCreateRoom()

    private fun popBack() =
        onPopBack()
}