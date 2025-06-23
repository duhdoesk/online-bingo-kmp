package ui.feature.createRoom.state

import domain.room.model.BingoType
import domain.theme.model.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
data class CreateScreenUiState constructor(
    val loading: Boolean,
    val name: String = "",
    val nameErrors: List<StringResource> = emptyList(),
    val locked: Boolean = false,
    val password: String = "",
    val passwordErrors: List<StringResource> = emptyList(),
    val availableThemes: List<Theme>,
    val maxWinners: Int = 1,
    val bingoType: BingoType,
    val selectedTheme: Theme?
) {
    companion object {
        @OptIn(ExperimentalResourceApi::class)
        val INITIAL = CreateScreenUiState(
            loading = true,
            name = "",
            nameErrors = listOf(),
            locked = false,
            password = "",
            passwordErrors = listOf(),
            availableThemes = listOf(),
            maxWinners = 1,
            bingoType = BingoType.CLASSIC,
            selectedTheme = null
        )
    }
}
