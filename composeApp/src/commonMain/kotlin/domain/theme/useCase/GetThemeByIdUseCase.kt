package domain.theme.useCase

import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository

class GetThemeByIdUseCase(private val themeRepository: BingoThemeRepository) {
    suspend operator fun invoke(themeId: String): Result<BingoTheme> {
        themeRepository.getThemeById(themeId).fold(
            onFailure = { return Result.failure(it) },
            onSuccess = { dto -> return Result.success(dto.toModel()) }
        )
    }
}
