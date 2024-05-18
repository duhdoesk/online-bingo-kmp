package ui.presentation.create_room.state

data class CreateScreenUiState(
    val name: String = "",
    val nameErrors: List<String> = emptyList(),
    val locked: Boolean = false,
    val password: String = "",
    val passwordErrors: List<String> = emptyList(),
    val themeId: String = "",
    val maxWinners: Int = 1
)