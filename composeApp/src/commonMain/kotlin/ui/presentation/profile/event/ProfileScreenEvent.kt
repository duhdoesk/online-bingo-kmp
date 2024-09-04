package ui.presentation.profile.event

sealed class ProfileScreenEvent {

    data object DeleteAccount : ProfileScreenEvent()
    data object PopBack : ProfileScreenEvent()
    data object SignOut : ProfileScreenEvent()
    data object UILoaded : ProfileScreenEvent()
    data object UpdatePassword : ProfileScreenEvent()
    data object UpdatePicture : ProfileScreenEvent()

    data class UpdateName(val newName: String) : ProfileScreenEvent()
    data class UpdateMessage(val newMessage: String) : ProfileScreenEvent()

    // Navigation Events
//    data object UpdatePassword: ProfileScreenUIEvent()
//    data object UpdatePicture: ProfileScreenUIEvent()

    // Action Events
//    data object DeleteAccount: ProfileScreenUIEvent()
//    data object PopBack: ProfileScreenUIEvent()
//    data object SignOut: ProfileScreenUIEvent()
//    data object UILoaded: ProfileScreenUIEvent()
//    data object UpdateName: ProfileScreenUIEvent()
//    data object UpdateMessage: ProfileScreenUIEvent()

    // Confirmation Events
//    data object ConfirmDeleteAccount: ProfileScreenUIEvent()
//    data object ConfirmSignOut: ProfileScreenUIEvent()
//    data class ConfirmUpdateName(val newName: String): ProfileScreenUIEvent()
//    data class ConfirmUpdateMessage(val newMessage: String): ProfileScreenUIEvent()

}