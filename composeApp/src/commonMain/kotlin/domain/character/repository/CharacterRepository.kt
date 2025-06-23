package domain.character.repository

import domain.character.model.Character
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    /** Returns a list of characters given a theme ID */
    fun getThemeCharacters(themeId: String): Flow<Resource<List<Character>>>

    /** Creates a character */
    fun uploadNewCharacter(
        themeId: String,
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String
    ): Flow<Resource<Unit>>
}
