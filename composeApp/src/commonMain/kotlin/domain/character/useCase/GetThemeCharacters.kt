package domain.character.useCase

import domain.character.model.Character
import domain.character.repository.CharacterRepository

class GetThemeCharacters(private val repository: CharacterRepository) {
    suspend operator fun invoke(themeId: String): Result<List<Character>> {
        repository.getThemeCharacters(themeId).fold(
            onFailure = { exception ->
                return Result.failure(exception)
            },
            onSuccess = { list ->
                val model = list.map { dto -> dto.toModel() }
                return Result.success(model)
            }
        )
    }
}
