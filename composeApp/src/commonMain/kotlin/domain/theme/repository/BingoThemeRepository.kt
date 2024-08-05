package domain.theme.repository

import domain.character.model.Character
import domain.theme.model.BingoTheme
import kotlinx.coroutines.flow.Flow

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): BingoTheme
    fun getAllThemes(): Flow<List<BingoTheme>>
    fun getCharacters(themeId: String): Flow<List<Character>>
}