package data.supabase

import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.appleNativeLogin
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth

private const val GOOGLE_SERVER_CLIENT_ID = "746232526782-mnq2lnm9fp2sunpopk0cfp7pu6v9trcu.apps.googleusercontent.com"
private const val SUPABASE_URL = "https://bpcxhabtfthefpxzphmf.supabase.co"
private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwY3hoYWJ0ZnRoZWZweHpwaG1mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTk3MDQxMjEsImV4cCI6MjAzNTI4MDEyMX0.HIe-CvhBghN8Rcd6thhMtBXrfB4KwYiwhCW3Dv1AHrI"

fun createThemeBingoSupabaseClient() = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY,
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