package domain.feature.appVersion

import domain.feature.appVersion.model.AppVersionUpdate
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AppVersionRepository {

    /**
     * Checks both the installed version and the minimum supported version.
     * Returns true if an update is required.
     */
    fun checkForUpdates(): Flow<Resource<AppVersionUpdate>>

    /** Returns the installed version of the app. */
    fun getInstalledVersion(): Flow<Resource<String>>
}
