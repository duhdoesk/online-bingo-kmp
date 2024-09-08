package ui.presentation.sign_in.state

data class SignInScreenUIState(
    val isLoading: Boolean,
) {
    companion object {
        val INITIAL = SignInScreenUIState(isLoading = true)
    }
}
