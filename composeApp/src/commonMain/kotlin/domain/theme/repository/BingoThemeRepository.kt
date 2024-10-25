package domain.theme.repository

import data.character.model.CharacterDTO
import data.theme.model.BingoThemeDTO
import kotlinx.coroutines.flow.Flow

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): Result<BingoThemeDTO>
    fun flowThemeById(id: String): Flow<BingoThemeDTO>
    fun getAllThemes(): Flow<List<BingoThemeDTO>>
    fun flowThemeCharacters(themeId: String): Flow<List<CharacterDTO>>
    suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>>
}