package ui.presentation.forgotPassword.event

sealed class ForgotPasswordEvent {
    data object SendPasswordResetEmail : ForgotPasswordEvent()
    data object PopBack : ForgotPasswordEvent()
    data class UpdateEmail(val email: String) : ForgotPasswordEvent()
}
