package ui.presentation.signIn

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.OCEAN
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: (userId: String?) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val audioPlayer by inject<AudioPlayer>()
    val supabaseClient: SupabaseClient by inject()

    init {
        if (!audioPlayer.isPlaying) {
            val media = Res.getUri(OCEAN)
            audioPlayer.playNew(media)
        }
    }

    /** Fetches user id then navigates passing it */
    fun signIn() {
        coroutineScope.launch {
            supabaseClient
                .auth
                .sessionStatus
                .collect { sessionStatus ->
                    when (sessionStatus) {
                        is SessionStatus.Authenticated -> {
                            audioPlayer.stop()
                            onSignIn(sessionStatus.session.user?.id)
                        }

                        else -> {
                            return@collect
                        }
                    }
                }
        }
    }
}
