package ui.presentation.home

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.SPLASHING_AROUND
import domain.billing.hasActiveEntitlements
import domain.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import ui.navigation.Configuration
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.state.HomeScreenUIState
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class HomeScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    private val onNavigate: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val audioPlayer by inject<AudioPlayer>()

    private val _uiState = MutableStateFlow(HomeScreenUIState.INITIAL)
    val uiState get() = _uiState.asStateFlow()

    fun uiEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.Navigate -> navigate(event.configuration)
            is HomeScreenEvent.UILoaded -> uiLoaded()
            is HomeScreenEvent.Reload -> uiLoaded()
        }
    }

    init {
        if (!audioPlayer.isPlaying) {
            audioPlayer.playNew(Res.getUri(SPLASHING_AROUND))
        }
    }

    private fun uiLoaded() {
        coroutineScope.launch {
            user.collect { collectedUser ->
                if (collectedUser != null) {
                    val isSubscribed = hasActiveEntitlements()

                    _uiState.update {
                        HomeScreenUIState(
                            loading = false,
                            userName = collectedUser.name,
                            userPicture = collectedUser.pictureUri,
                            isSubscribed = isSubscribed,
                            error = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = true
                        )
                    }
                }
            }
        }
    }

    private fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}
