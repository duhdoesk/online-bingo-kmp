package ui.presentation.profile.event

sealed class ProfileScreenEvent {

    data object DeleteAccount : ProfileScreenEvent()
    data object PopBack : ProfileScreenEvent()
    data object SignOut : ProfileScreenEvent()
    data object UILoaded : ProfileScreenEvent()

    data class UpdateMessage(val newMessage: String) : ProfileScreenEvent()
    data class UpdateName(val newName: String) : ProfileScreenEvent()
    data class UpdatePicture(val newPictureUri: String) : ProfileScreenEvent()
}
