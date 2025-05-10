package domain.feature.appVersion.useCase

import domain.feature.appVersion.AppVersionRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetInstalledVersionUseCase(private val repository: AppVersionRepository) {

    /** Returns the installed version of the app. */
    operator fun invoke(): Flow<Resource<String>> {
        return repository.getInstalledVersion()
    }
}
