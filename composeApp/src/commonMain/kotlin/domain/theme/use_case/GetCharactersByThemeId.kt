package domain.theme.use_case

import domain.theme.repository.BingoThemeRepository

data class GetCharactersByThemeId(private val repository: BingoThemeRepository) {
    operator fun invoke(themeId: String) = repository.flowThemeCharacters(themeId)
}