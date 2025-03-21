package ui.presentation.signIn.state

data class SignInScreenUIState(
    val isLoading: Boolean
) {
    companion object {
        val INITIAL = SignInScreenUIState(isLoading = true)
    }
}
