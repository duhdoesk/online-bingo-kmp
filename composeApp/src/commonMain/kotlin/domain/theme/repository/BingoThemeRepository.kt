package domain.theme.repository

import data.character.model.CharacterDTO
import data.theme.model.BingoThemeDTO
import domain.character.model.Character
import domain.theme.model.BingoTheme
import kotlinx.coroutines.flow.Flow

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): Result<BingoThemeDTO>
    fun flowThemeById(id: String): Flow<BingoTheme>
    fun getAllThemes(): Flow<List<BingoTheme>>
    fun flowThemeCharacters(themeId: String): Flow<List<Character>>
    suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>>
}