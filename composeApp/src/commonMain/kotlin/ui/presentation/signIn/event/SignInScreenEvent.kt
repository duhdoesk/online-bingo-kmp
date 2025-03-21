package ui.presentation.signIn.event

sealed class SignInScreenEvent {
    data object UiLoaded : SignInScreenEvent()
    data object SignIn : SignInScreenEvent()
}
