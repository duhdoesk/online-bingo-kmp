package ui.presentation.signIn

import com.arkivanov.decompose.ComponentContext
import domain.audio.AudioPlayer
import io.github.jan.supabase.SupabaseClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInScreenComponent(
    componentContext: ComponentContext,
    val supabaseClient: SupabaseClient,
    private val onSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val audioPlayer by inject<AudioPlayer>()

    /**
     * Function to navigate when sign in is successful
     */
    fun signIn() {
        if (audioPlayer.isPlaying) {
            audioPlayer.stop()
        }
        onSignIn()
    }
}
