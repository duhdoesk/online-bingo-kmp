package ui.presentation.sign_in.event

sealed class SignInScreenEvent {
    data object UiLoaded: SignInScreenEvent()
    data object SignIn: SignInScreenEvent()
}
