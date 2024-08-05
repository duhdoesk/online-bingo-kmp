package domain.theme.use_case

import domain.theme.repository.BingoThemeRepository

data class GetAllThemes(private val repository: BingoThemeRepository) {
    operator fun invoke() = repository.getAllThemes()
}