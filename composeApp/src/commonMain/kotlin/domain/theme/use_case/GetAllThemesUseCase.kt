package domain.theme.use_case

import domain.theme.repository.BingoThemeRepository

data class GetAllThemesUseCase(private val repository: BingoThemeRepository) {
    operator fun invoke() = repository.getAllThemes()
}