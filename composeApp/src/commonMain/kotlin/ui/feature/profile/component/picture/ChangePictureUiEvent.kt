package ui.feature.profile.component.picture

import domain.profilePictures.model.ProfilePictures

sealed class ChangePictureUiEvent {
    data class SelectCategory(val category: ProfilePictures.Category) : ChangePictureUiEvent()
    data object ClearCategories : ChangePictureUiEvent()
}
