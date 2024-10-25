package domain.theme.use_case

import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.map

data class GetAllThemesUseCase(private val repository: BingoThemeRepository) {
    operator fun invoke() = repository
        .getAllThemes()
        .map { list ->
            list.map { dto ->
                dto.toModel()
            }
        }
}