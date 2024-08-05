package ui.presentation.profile.picture

import domain.user.use_case.ProfilePictures

data class EditProfilePictureUiState(
    /**
     * Represents the screen loading state
     * */
    val loading: Boolean,

    /**
     * Represents the current user id
     * */
    val userId: String?,

    /**
     * Represents the current user name
     * */
    val userName: String?,

    /**
     * Represents the user current picture url
     * */
    val currentPictureUrl: String?,

    /**
     * Represents the currently selected picture url
     * */
    val selectedPictureUrl: String?,

    /**
     * Represents the available pictures the user can choose from
     * */
    val pictures: ProfilePictures?
) {
    companion object {
        val INITIAL = EditProfilePictureUiState(
            loading = true,
            userId = null,
            userName = null,
            currentPictureUrl = null,
            selectedPictureUrl = null,
            pictures = null
        )
    }
}