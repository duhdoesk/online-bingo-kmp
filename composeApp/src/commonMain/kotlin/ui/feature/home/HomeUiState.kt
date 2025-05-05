package ui.feature.home

import domain.feature.user.model.Tier

data class HomeUiState(
    val isLoading: Boolean = true,
    val username: String = "",
    val message: String = "",
    val pictureUrl: String = "",
    val tier: Tier = Tier.FREE
)
