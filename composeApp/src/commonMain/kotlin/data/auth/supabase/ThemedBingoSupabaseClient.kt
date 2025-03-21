package data.auth.supabase

import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.appleNativeLogin
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth

fun createThemeBingoSupabaseClient() = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    install(Auth) {
        host = "com.duhdoesk.themedbingocardsgenerator.ThemedBingo"
        scheme = "themedbingo"
    }
    install(ComposeAuth) {
        googleNativeLogin(serverClientId = GOOGLE_SERVER_CLIENT_ID)
        appleNativeLogin()
    }
}
