package ui.presentation.home.state

data class HomeScreenUIState(
    val loading: Boolean,
    val userName: String,
    val userPicture: String,
) {
    companion object {
        val INITIAL = HomeScreenUIState(
            loading = true,
            userName = "",
            userPicture = "",
        )
    }
}