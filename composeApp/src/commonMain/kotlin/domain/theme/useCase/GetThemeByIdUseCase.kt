package domain.theme.useCase

import domain.theme.model.Theme
import domain.theme.repository.ThemeRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetThemeByIdUseCase(private val themeRepository: ThemeRepository) {
    operator fun invoke(themeId: String): Flow<Resource<Theme>> {
        return themeRepository.getThemeById(themeId)
    }
}
