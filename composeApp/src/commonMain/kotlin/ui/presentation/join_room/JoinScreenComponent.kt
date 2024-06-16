package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext
import data.room.repository.BingoBingoRoomRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ui.navigation.Configuration
import util.componentCoroutineScope

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
) : ComponentContext by componentContext {

    private val _roomsList = BingoBingoRoomRepositoryImpl()
        .getRooms()

    val roomsList = _roomsList
        .stateIn(
            componentCoroutineScope(),
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun joinRoom(roomId: String) =
        onJoinRoom(Configuration.PlayScreen(roomId))

    fun popBack() =
        onPopBack()
}