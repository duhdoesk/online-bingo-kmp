package domain.theme.use_case

import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlowThemeByIdUseCase(private val bingoThemeRepository: BingoThemeRepository) {
    operator fun invoke(themeId: String): Flow<BingoTheme> = flow {
        bingoThemeRepository.getThemeById(themeId).map { it.toModel() }
    }
}