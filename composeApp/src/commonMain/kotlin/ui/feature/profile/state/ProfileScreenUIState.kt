package ui.feature.profile.state

import domain.profilePictures.ProfilePictures
import domain.user.model.User

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
