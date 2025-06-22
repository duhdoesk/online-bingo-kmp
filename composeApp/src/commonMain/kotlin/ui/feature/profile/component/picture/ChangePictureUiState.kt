package ui.feature.profile.component.picture

import domain.profilePictures.model.ProfilePictures.Category

data class ChangePictureUiState(
    val isLoading: Boolean = true,
    val allCategories: List<Category> = emptyList(),
    val selectedCategories: List<Category> = emptyList(),
    val availablePictures: List<String> = emptyList()
)
