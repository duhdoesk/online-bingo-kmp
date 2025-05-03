package ui.feature.home

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.SPLASHING_AROUND
import domain.billing.hasActiveEntitlements
import domain.user.useCase.GetSignedInUserUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import ui.navigation.Configuration
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: (configuration: Configuration) -> Unit,
    private val onUserNotCreated: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val audioPlayer: AudioPlayer by inject()
    private val getSignedInUser: GetSignedInUserUseCase by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeScreenUIState> = getSignedInUser()
        .map { user ->
            when (user) {
                is Resource.Success -> {
                    val isSubscribed = hasActiveEntitlements()

                    HomeScreenUIState(
                        loading = false,
                        userName = user.data.name,
                        userPicture = user.data.pictureUri,
                        isSubscribed = isSubscribed
                    )
                }

                is Resource.Failure -> {
                    onUserNotCreated()
                    HomeScreenUIState(loading = false)
                }
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenUIState()
        )

    init {
        coroutineScope.launch {
            if (!audioPlayer.isPlaying) {
                delay(1000)
                audioPlayer.playNew(Res.getUri(SPLASHING_AROUND))
            }
        }
    }

    fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}
