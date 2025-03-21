package ui.presentation.changePassword.event

sealed class ChangePasswordScreenUIEvent {
    data object PopBack : ChangePasswordScreenUIEvent()
    data object ConfirmPasswordChange : ChangePasswordScreenUIEvent()
    data class CurrentPasswordTyping(val input: String) : ChangePasswordScreenUIEvent()
    data class NewPasswordTyping(val input: String) : ChangePasswordScreenUIEvent()
    data class RepeatPasswordTyping(val input: String) : ChangePasswordScreenUIEvent()
}
