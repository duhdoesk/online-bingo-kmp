package ui.presentation.signIn

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val audioPlayer by inject<AudioPlayer>()
    val supabaseClient: SupabaseClient by inject()

    /**
     * Function to navigate when sign in is successful
     */
    fun signIn() {
        coroutineScope.launch {
            if (audioPlayer.isPlaying) {
                audioPlayer.stop()
            }
            onSignIn()
        }
    }
}
