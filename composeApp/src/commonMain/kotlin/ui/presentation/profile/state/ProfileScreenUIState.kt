package ui.presentation.profile.state

import domain.user.model.User
import domain.user.useCase.ProfilePictures

data class ProfileScreenUIState(
    val isLoading: Boolean,
    val user: User?,
    val error: Boolean,
    val profilePictures: ProfilePictures?
) {
    companion object {
        val INITIAL = ProfileScreenUIState(
            isLoading = true,
            user = null,
            error = false,
            profilePictures = null
        )
    }
}
