package ui.feature.home

data class HomeScreenUIState(
    val loading: Boolean = true,
    val userName: String = "",
    val userPicture: String = "",
    val isSubscribed: Boolean = false
)
