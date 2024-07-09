package ui.presentation.sign_up.event

sealed class SignUpScreenEvent {
    data object SignUp: SignUpScreenEvent()
    data object PopBack: SignUpScreenEvent()

    data class UpdateEmail(val email: String): SignUpScreenEvent()
    data class UpdatePassword1(val password: String): SignUpScreenEvent()
    data class UpdatePassword2(val password: String): SignUpScreenEvent()
}