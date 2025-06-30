package ui.feature.lobby

import com.arkivanov.decompose.ComponentContext
import domain.billing.hasActiveEntitlements
import domain.feature.user.useCase.GetCurrentUserUseCase
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.room.useCase.GetAvailableRoomsUseCase
import domain.room.useCase.JoinRoomUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_user_not_found
import themedbingo.composeapp.generated.resources.join_room_invalid_password
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.navigation.Configuration
import util.componentCoroutineScope

class LobbyScreenComponent(
    componentContext: ComponentContext,
    val bingoType: BingoType,
    private val onPopBack: () -> Unit,
    private val onJoinRoom: (configuration: Configuration) -> Unit,
    private val onCreateRoom: () -> Unit,
    private val onNavigateToPaywall: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /** Coroutine Scope to handle each suspend operation */
    private val coroutineScope = componentCoroutineScope()

    /** Use Cases */
    private val getAvailableRoomsUseCase: GetAvailableRoomsUseCase by inject()
    private val joinRoomUseCase: JoinRoomUseCase by inject()
    private val getCurrentUserUseCase: GetCurrentUserUseCase by inject()

    /** Ui Effects Channel */
    private val _uiEffect = Channel<LobbyScreenUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    /** UI State holders */
    private val _trigger = Channel<Unit>()
    private val _openRooms = getAvailableRoomsUseCase(bingoType)
    private val _query = MutableStateFlow("")
    private val _user = getCurrentUserUseCase()
    private val _loading = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<LobbyScreenUiState> = _trigger
        .receiveAsFlow()
        .onStart { emit(Unit) }
        .flatMapLatest {
            combine(
                _query,
                _openRooms,
                _user,
                _loading
            ) { query, roomsRes, userRes, loading ->
                val user = userRes.getOrNull()
                if (user == null) {
                    return@combine LobbyScreenUiState(
                        isLoading = false,
                        isError = true
                    )
                }

                val rooms = roomsRes.getOrNull()
                if (rooms == null) {
                    return@combine LobbyScreenUiState(
                        isLoading = false,
                        isError = true
                    )
                }

                LobbyScreenUiState(
                    isLoading = loading,
                    isError = false,
                    availableRooms = rooms.filter {
                        val name = it.name.lowercase().contains(query.lowercase())
                        val host = it.host.name.lowercase().contains(query.lowercase())
                        val theme =
                            it.type is BingoRoom.Type.Themed &&
                                it.type.theme.name.lowercase().contains(query.lowercase())

                        name || host || theme
                    }.sortedBy { it.createdAt },
                    query = query,
                    isSubscribed = hasActiveEntitlements(),
                    user = user
                )
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LobbyScreenUiState()
        )

    /**
     * UI Event handling delegate function
     */
    fun uiEvent(event: LobbyScreenUiEvent) {
        when (event) {
            LobbyScreenUiEvent.OnCreateRoom -> {
                createRoom()
            }

            LobbyScreenUiEvent.OnNavigateToPaywall -> {
                onNavigateToPaywall()
            }

            LobbyScreenUiEvent.OnPopBack -> {
                popBack()
            }

            LobbyScreenUiEvent.OnRetry -> {
                retry()
            }

            is LobbyScreenUiEvent.OnJoinRoom -> {
                joinRoom(
                    room = event.room,
                    password = event.roomPassword
                )
            }

            is LobbyScreenUiEvent.OnQueryChange -> {
                updateQuery(event.query)
            }
        }
    }

    private fun retry() {
        coroutineScope.launch {
            _trigger.send(Unit)
        }
    }

    private fun updateQuery(query: String) {
        _query.update { query }
    }

    private fun joinRoom(room: BingoRoom, password: String?) {
        coroutineScope.launch {
            _loading.update { true }

            /** Emits an error and returns if passwords did not match */
            if (room.privacy is RoomPrivacy.Private && room.privacy.password != password) {
                val message = getString(Res.string.join_room_invalid_password)
                _uiEffect.send(LobbyScreenUiEffect.ShowSnackbar(message))
                return@launch
            }

            /** Emits an error and returns if the user is not found */
            val userId = getCurrentUserUseCase.invoke().first().getOrNull()?.id
            if (userId == null) {
                val message = getString(Res.string.auth_user_not_found)
                _uiEffect.send(LobbyScreenUiEffect.ShowSnackbar(message))
                return@launch
            }

            /** Checks if user is host and joins accordingly */
            if (userId == room.host.id) {
                joinAsHost(room.id)
            } else {
                joinAsPlayer(room.id, userId)
            }

            _loading.update { false }
        }
    }

    private fun joinAsHost(roomId: String) {
        when (bingoType) {
            BingoType.CLASSIC -> onJoinRoom(Configuration.HostScreenClassic(roomId))
            BingoType.THEMED -> onJoinRoom(Configuration.HostScreenThemed(roomId))
        }
    }

    private suspend fun joinAsPlayer(roomId: String, userId: String) {
        joinRoomUseCase.invoke(roomId = roomId, userId = userId)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        when (bingoType) {
                            BingoType.CLASSIC -> Configuration.PlayerScreenClassic(roomId)
                            BingoType.THEMED -> Configuration.PlayerScreenThemed(roomId)
                        }.run {
                            onJoinRoom(this)
                        }
                    }

                    is Resource.Failure -> {
                        _uiEffect.send(
                            LobbyScreenUiEffect.ShowSnackbar(getString(Res.string.unmapped_error))
                        )
                    }
                }
            }
    }

    private fun createRoom() {
        onCreateRoom()
    }

    private fun popBack() {
        onPopBack()
    }
}
