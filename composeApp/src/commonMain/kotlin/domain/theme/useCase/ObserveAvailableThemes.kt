package domain.theme.useCase

import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAvailableThemes(private val themeRepository: BingoThemeRepository) {
    operator fun invoke(): Flow<List<BingoTheme>> {
        return themeRepository
            .observeAvailableThemes()
            .map { list ->
                list.map { dto ->
                    dto.toModel()
                }
            }
    }
}
