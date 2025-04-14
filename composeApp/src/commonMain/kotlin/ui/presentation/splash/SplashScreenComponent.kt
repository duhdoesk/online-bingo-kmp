package ui.presentation.splash

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.OCEAN
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class SplashScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToHome: () -> Unit,
    private val onNavigateToSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val supabaseClient by inject<SupabaseClient>()
    private val audioPlayer by inject<AudioPlayer>()

    private val _progress = MutableStateFlow<Float>(0f)
    val progress = _progress.asStateFlow()

    init {
        coroutineScope.launch {
            delay(1000)
            audioPlayer.playNew(Res.getUri(OCEAN))
            checkSessionStatus()
        }
    }

    private suspend fun checkSessionStatus() {
        supabaseClient.auth.sessionStatus.collect {
            _progress.update { 1f }
            delay(2000)

            when (it) {
                is SessionStatus.Authenticated -> {
                    audioPlayer.stop()
                    onNavigateToHome()
                }
                else -> {
                    onNavigateToSignIn()
                }
            }
        }
    }
}
