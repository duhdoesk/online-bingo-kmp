package ui.feature.profile

import domain.feature.user.model.User

sealed class ProfileScreenUiState {
    data object Loading : ProfileScreenUiState()
    data class Error(val message: String) : ProfileScreenUiState()
    data class Success(val user: User) : ProfileScreenUiState()
}
