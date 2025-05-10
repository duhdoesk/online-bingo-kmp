package domain.feature.appVersion.useCase

import domain.feature.appVersion.AppVersionRepository
import domain.feature.appVersion.model.AppVersionUpdate
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class CheckForUpdatesUseCase(private val repository: AppVersionRepository) {

    /**
     * Checks both the installed version and the minimum supported version.
     * Returns true if an update is required.
     */
    operator fun invoke(): Flow<Resource<AppVersionUpdate>> {
        return repository.checkForUpdates()
    }
}
