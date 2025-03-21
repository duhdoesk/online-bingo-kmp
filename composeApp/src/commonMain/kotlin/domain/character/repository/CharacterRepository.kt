package domain.character.repository

import data.character.model.CharacterDTO
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun observeThemeCharacters(themeId: String): Flow<List<CharacterDTO>>
    suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>>
}
