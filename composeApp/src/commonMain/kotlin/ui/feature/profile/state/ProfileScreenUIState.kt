package ui.feature.profile.state

import domain.feature.user.model.User
import domain.profilePictures.ProfilePictures

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
