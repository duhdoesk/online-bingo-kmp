package ui.feature.createRoom

import com.arkivanov.decompose.ComponentContext
import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.room.useCase.CreateRoomUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_error
import ui.navigation.Configuration
import util.componentCoroutineScope

class CreateRoomScreenComponent(
    componentContext: ComponentContext,
    bingoType: BingoType,
    private val onPopBack: () -> Unit,
    private val onRoomCreated: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /** Coroutine Scope to handle each suspend operation */
    private val coroutineScope = componentCoroutineScope()

    /** Use Cases */
    private val createRoomUseCase: CreateRoomUseCase by inject()

    private val _uiMessage = Channel<String>()
    val uiMessage = _uiMessage.receiveAsFlow()

    /** UI State holder */
    private val _name = MutableStateFlow<String>("")
    private val _maxWinners = MutableStateFlow<Int>(1)
    private val _privacy = MutableStateFlow<RoomPrivacy>(RoomPrivacy.Open)
    private val _type = MutableStateFlow<CreateRoomUiState.Type>(
        CreateRoomUiState.Type.parseFromEnum(bingoType)
    )

    val uiState = combine(
        _name,
        _maxWinners,
        _privacy,
        _type
    ) { name, maxWinners, privacy, type ->
        val canProceed = {
            val name = name.trim().length > 2
            val privacy =
                privacy is RoomPrivacy.Open || privacy is RoomPrivacy.Private && privacy.password.length > 2
            val type =
                type == CreateRoomUiState.Type.Classic || type is CreateRoomUiState.Type.Themed && type.theme != null
            name && privacy && type
        }

        CreateRoomUiState(
            isLoading = false,
            canProceed = canProceed(),
            name = name,
            privacy = privacy,
            maxWinners = maxWinners,
            type = type
        )
    }
        .stateIn(
            coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CreateRoomUiState()
        )

    /** Delegate the responsibility for the handling of each UI Event */
    fun onUiEvent(event: CreateRoomUiEvent) {
        when (event) {
            CreateRoomUiEvent.OnCreateRoom -> onCreateRoom()
            CreateRoomUiEvent.OnPopBack -> onPopBack()
            is CreateRoomUiEvent.OnChangePrivacy -> onChangePrivacy(event.privacy)
            is CreateRoomUiEvent.OnChangeMaxWinners -> onChangeMaxWinners(event.maxWinners)
            is CreateRoomUiEvent.OnChangeName -> onChangeName(event.name)
            is CreateRoomUiEvent.OnChangeType -> onChangeType(event.type)
        }
    }

    private fun onCreateRoom() {
        coroutineScope.launch {
            val type = _type.value.toEnum()
            val themeId = _type.value.let {
                if (it is CreateRoomUiState.Type.Themed) it.theme?.id else null
            }

            createRoomUseCase.invoke(
                name = _name.value.trim(),
                privacy = _privacy.value,
                maxWinners = _maxWinners.value,
                type = type,
                themeId = themeId
            )
                .take(1)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val configuration = when (type) {
                                BingoType.THEMED -> Configuration.HostScreenThemed(resource.data)
                                BingoType.CLASSIC -> Configuration.HostScreenClassic(resource.data)
                            }
                            onRoomCreated(configuration)
                        }

                        is Resource.Failure -> {
                            _uiMessage.trySend(getString(Res.string.create_error))
                        }
                    }
                }
        }
    }

    private fun onChangePrivacy(privacy: RoomPrivacy) {
        _privacy.update { privacy }
    }

    private fun onChangeMaxWinners(maxWinners: Int) {
        _maxWinners.update { maxWinners }
    }

    private fun onChangeName(name: String) {
        _name.update { name }
    }

    private fun onChangeType(type: CreateRoomUiState.Type) {
        _type.update { type }
    }
}
