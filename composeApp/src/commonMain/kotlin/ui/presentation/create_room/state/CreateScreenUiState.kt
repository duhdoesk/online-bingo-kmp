package ui.presentation.create_room.state

import domain.room.model.BingoType
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

data class CreateScreenUiState @OptIn(ExperimentalResourceApi::class) constructor(
    val loading: Boolean,
    val name: String = "",
    val nameErrors: List<StringResource> = emptyList(),
    val locked: Boolean = false,
    val password: String = "",
    val passwordErrors: List<StringResource> = emptyList(),
    val availableThemes: List<BingoTheme>,
    val themeId: String = "",
    val maxWinners: Int = 1,
    val bingoType: BingoType,
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
            themeId = "",
            maxWinners = 1,
            bingoType = BingoType.CLASSIC,
        )
    }
}