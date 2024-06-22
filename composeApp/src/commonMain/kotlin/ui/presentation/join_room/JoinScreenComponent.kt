package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext
import data.room.repository.BingoRoomRepositoryImpl
import domain.room.repository.BingoRoomRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import util.componentCoroutineScope

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val roomRepository by inject<BingoRoomRepository>()
    private val _roomsList = roomRepository
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