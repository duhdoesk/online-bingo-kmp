package ui.feature.profile

sealed class ProfileScreenUiEvent {
    data class UpdateMessage(val message: String) : ProfileScreenUiEvent()
    data class UpdateName(val name: String) : ProfileScreenUiEvent()
    data class UpdatePicture(val pictureUrl: String) : ProfileScreenUiEvent()
    data object DeleteAccount : ProfileScreenUiEvent()
    data object PopBack : ProfileScreenUiEvent()
    data object Retry : ProfileScreenUiEvent()
    data object SignOut : ProfileScreenUiEvent()
}
