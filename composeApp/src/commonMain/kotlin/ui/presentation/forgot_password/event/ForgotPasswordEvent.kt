package ui.presentation.forgot_password.event

sealed class ForgotPasswordEvent {
    data object SendPasswordResetEmail: ForgotPasswordEvent()
    data object PopBack: ForgotPasswordEvent()
    data class UpdateEmail(val email: String): ForgotPasswordEvent()
}