package ui.presentation.changePassword.state

data class ChangePasswordScreenUIState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val repeatPassword: String = ""
)
