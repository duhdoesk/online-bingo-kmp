package ui.presentation.profile.state

import domain.user.model.User

sealed class ProfileScreenUIState {
    data object Loading: ProfileScreenUIState()
    data object Error: ProfileScreenUIState()
    data class Success(val user: User): ProfileScreenUIState()
}