package ui.feature.createUser

import domain.profilePictures.ProfilePictures

data class CreateUserUiState(
    val loading: Boolean = true,
    val name: String = "",
    val message: String = "",
    val pictureUri: String = "",
    val profilePictures: ProfilePictures? = null,
    val canProceed: Boolean = false
)
