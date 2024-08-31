package ui.presentation.sign_in.event

import io.github.jan.supabase.compose.auth.composable.NativeSignInResult

sealed class SignInScreenEvent {
    data object UiLoaded: SignInScreenEvent()
    data class SignInWithGoogle(val result: NativeSignInResult): SignInScreenEvent()
    data class SignInWithApple(val result: NativeSignInResult): SignInScreenEvent()
}
