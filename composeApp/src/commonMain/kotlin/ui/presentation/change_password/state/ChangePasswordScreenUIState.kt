package ui.presentation.change_password.state

data class ChangePasswordScreenUIState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val repeatPassword: String = "",
)