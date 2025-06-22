package domain.theme.useCase

import domain.theme.model.Theme
import domain.theme.repository.ThemeRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

data class GetAllThemesUseCase(private val repository: ThemeRepository) {
    operator fun invoke(): Flow<Resource<List<Theme>>> {
        return repository.getAllThemes()
    }
}
