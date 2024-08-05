package ui.presentation.profile.picture

sealed class EditProfilePictureUiEvent {
    data object OnUiLoaded : EditProfilePictureUiEvent()
    data class OnPictureSelected(val pictureUrl: String) : EditProfilePictureUiEvent()
    data object OnCancel : EditProfilePictureUiEvent()
    data object OnConfirm : EditProfilePictureUiEvent()
}