import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DeeplinkHandler : KoinComponent {

    private val supabase: SupabaseClient by inject()

    fun handleIOSLaunch(url: platform.Foundation.NSURL) {
        supabase.handleDeeplinks(url)
    }
}
