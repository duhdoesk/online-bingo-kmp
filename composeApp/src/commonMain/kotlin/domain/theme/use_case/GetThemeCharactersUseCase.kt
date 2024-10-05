package domain.theme.use_case

import domain.character.model.Character
import domain.theme.repository.BingoThemeRepository

class GetThemeCharactersUseCase(private val themeRepository: BingoThemeRepository) {
    suspend operator fun invoke(themeId: String): Result<List<Character>> {
        themeRepository.getThemeCharacters(themeId).fold(
            onFailure = { exception ->
                return Result.failure(exception)
            },
            onSuccess = { dto ->
                val model = dto.map { it.toModel() }
                return Result.success(model)
            }
        )
    }
}