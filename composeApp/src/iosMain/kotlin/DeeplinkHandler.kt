import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.handleDeeplinks
import io.ktor.http.Url
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DeeplinkHandler : KoinComponent {

    private val supabase: SupabaseClient by inject()

    fun handleIOSLaunch(url: String) {
        val NSURL = platform.Foundation.NSURL.URLWithString(url) ?: return
        supabase.handleDeeplinks(NSURL)
    }

}