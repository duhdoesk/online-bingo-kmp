package ui.presentation.home

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.SPLASHING_AROUND
import domain.billing.hasActiveEntitlements
import domain.user.useCase.ObserveUserUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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
    private val onUserNotAuthenticated: () -> Unit,
    private val onUserNotCreated: (UserInfo?) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val audioPlayer: AudioPlayer by inject()
    private val supabaseClient: SupabaseClient by inject()
    private val observeUserUseCase: ObserveUserUseCase by inject()

    private val _userInfo = supabaseClient
        .auth
        .sessionStatus
        .map { session ->
            when (session) {
                is SessionStatus.Authenticated -> session.session.user
                else -> null
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeScreenUIState> = _userInfo
        .flatMapLatest { userInfo ->
            if (userInfo == null) {
                onUserNotAuthenticated()
                return@flatMapLatest flow { emit(HomeScreenUIState(loading = false)) }
            }

            observeUserUseCase(userInfo.id)
                .map { user ->
                    if (user == null) {
                        onUserNotCreated(userInfo)
                        HomeScreenUIState(loading = false)
                    } else {
                        val isSubscribed = hasActiveEntitlements()
                        HomeScreenUIState(
                            loading = false,
                            userName = user.name,
                            userPicture = user.pictureUri,
                            isSubscribed = isSubscribed
                        )
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
