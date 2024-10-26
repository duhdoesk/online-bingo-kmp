package domain.character.use_case

import domain.character.model.Character
import domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ObserveThemeCharacters(private val repository: CharacterRepository) {
    operator fun invoke(themeId: String): Flow<List<Character>> {
        return repository
            .observeThemeCharacters(themeId)
            .map { list ->
                list.map { dto ->
                    dto.toModel()
                }
            }
    }
}