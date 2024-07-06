package ui.presentation.sign_in.event

sealed class SignInScreenEvent {
    data object SignIn: SignInScreenEvent()
    data object SignUp: SignInScreenEvent()
    data object SendPasswordResetEmail: SignInScreenEvent()

    data class UpdateEmail(val email: String): SignInScreenEvent()
    data class UpdatePassword(val password: String): SignInScreenEvent()
}
