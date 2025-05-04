package data.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.appleNativeLogin
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

fun createThemeBingoSupabaseClient() = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    /** Authentication module (core) */
    install(Auth) {
        host = "com.duhdoesk.themedbingocardsgenerator.ThemedBingo"
        scheme = "themedbingo"
    }

    /** Authentication module (compose) */
    install(ComposeAuth) {
        googleNativeLogin(serverClientId = GOOGLE_SERVER_CLIENT_ID)
        appleNativeLogin()
    }

    /** Postgrest module */
    install(Postgrest)

    /** Realtime module */
    install(Realtime)
}
