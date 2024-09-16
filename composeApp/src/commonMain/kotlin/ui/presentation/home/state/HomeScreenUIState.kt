package ui.presentation.home.state

data class HomeScreenUIState(
    val loading: Boolean,
    val userName: String,
    val userPicture: String,
    val isSubscribed: Boolean,
) {
    companion object {
        val INITIAL = HomeScreenUIState(
            loading = true,
            userName = "",
            userPicture = "",
            isSubscribed = false,
        )
    }
}