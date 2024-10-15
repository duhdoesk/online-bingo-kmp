package ui.presentation.profile.state

import domain.user.model.User

data class ProfileScreenUIState(
    val isLoading: Boolean,
    val user: User?,
    val error: Boolean,
) {
    companion object {
        val INITIAL = ProfileScreenUIState(
            isLoading = true,
            user = null,
            error = false,
        )
    }
}