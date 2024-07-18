package ui.presentation.profile.event

sealed class ProfileScreenEvent {
    data object PopBack: ProfileScreenEvent()
    data object SignOut: ProfileScreenEvent()
    data object DeleteAccount: ProfileScreenEvent()
    data object UpdatePicture: ProfileScreenEvent()
    data class UpdatePassword(val newPassword: String, val currentPassword: String): ProfileScreenEvent()
    data class UpdateName(val name: String): ProfileScreenEvent()
    data class UpdateVictoryMessage(val victoryMessage: String): ProfileScreenEvent()
}