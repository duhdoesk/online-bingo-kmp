package ui.presentation.signIn

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import domain.audio.OCEAN
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import themedbingo.composeapp.generated.resources.Res
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: () -> Unit
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

    /**
     * Function to navigate when sign in is successful
     */
    fun signIn() {
        coroutineScope.launch {
            audioPlayer.stop()
            onSignIn()
        }
    }
}
