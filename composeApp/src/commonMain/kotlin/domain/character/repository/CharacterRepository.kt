package domain.character.repository

import data.character.model.CharacterDTO
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun observeThemeCharacters(themeId: String): Flow<List<CharacterDTO>>
    suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>>

    fun uploadNewCharacter(
        themeId: String,
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String
    ): Flow<Resource<Unit>>
}
