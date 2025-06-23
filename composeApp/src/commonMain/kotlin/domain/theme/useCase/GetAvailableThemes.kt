package domain.theme.useCase

import domain.theme.model.Theme
import domain.theme.repository.ThemeRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetAvailableThemes(private val themeRepository: ThemeRepository) {
    operator fun invoke(): Flow<Resource<List<Theme>>> {
        return themeRepository.getAvailableThemes()
    }
}
