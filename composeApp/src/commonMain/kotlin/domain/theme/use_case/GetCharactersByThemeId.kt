package domain.theme.use_case

import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.map

data class GetCharactersByThemeId(private val repository: BingoThemeRepository) {
    operator fun invoke(themeId: String) = repository
        .flowThemeCharacters(themeId)
        .map { list ->
            list.map { dto ->
                dto.toModel()
            }
        }
}