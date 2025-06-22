package domain.character.useCase

import domain.character.model.Character
import domain.character.repository.CharacterRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

data class GetThemeCharacters(private val repository: CharacterRepository) {
    operator fun invoke(themeId: String): Flow<Resource<List<Character>>> {
        return repository.getThemeCharacters(themeId)
    }
}
