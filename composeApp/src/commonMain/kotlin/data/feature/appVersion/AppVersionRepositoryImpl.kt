@file:OptIn(SupabaseExperimental::class)

package data.feature.appVersion

import data.feature.appVersion.model.AppVersionDto
import data.supabase.supabaseCall
import domain.feature.appVersion.AppVersionRepository
import domain.feature.appVersion.model.AppVersionUpdate
import domain.util.resource.Resource
import getPlatform
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import util.Log

class AppVersionRepositoryImpl(private val supabaseClient: SupabaseClient) : AppVersionRepository {

    override fun checkForUpdates(): Flow<Resource<AppVersionUpdate>> {
        val platform = getPlatform().system
        val installed = getPlatformInstalledVersion().getOrNull().toString()

        return supabaseCall {
            supabaseClient
                .from("app_versions")
                .selectSingleValueAsFlow(AppVersionDto::platform) { eq("platform", platform) }
        }
            .map { resource ->
                Log.d(
                    message = "Installed: $installed. Min Supported: ${resource.getOrNull()?.minSupportedVersion}.",
                    tag = AppVersionRepositoryImpl::class.simpleName.toString()
                )
                when (resource) {
                    is Resource.Success -> {
                        val isOutdated = this@AppVersionRepositoryImpl.isVersionOutdated(
                            minimumSupported = resource.data.minSupportedVersion,
                            installed = installed
                        )

                        if (isOutdated) {
                            Resource.Success(
                                AppVersionUpdate.UpdateRequired(resource.data.updateUrl)
                            )
                        } else {
                            Resource.Success(AppVersionUpdate.NoUpdateRequired)
                        }
                    }

                    is Resource.Failure -> {
                        resource
                    }
                }
            }
    }

    override fun getInstalledVersion(): Flow<Resource<String>> = flow {
        emit(getPlatformInstalledVersion())
    }

    private fun isVersionOutdated(installed: String, minimumSupported: String): Boolean {
        val installedParts = installed.split(".").map { it.toIntOrNull() ?: 0 }
        val minimumParts = minimumSupported.split(".").map { it.toIntOrNull() ?: 0 }

        val maxLength = maxOf(installedParts.size, minimumParts.size)
        val paddedInstalled = installedParts + List(maxLength - installedParts.size) { 0 }
        val paddedMinimum = minimumParts + List(maxLength - minimumParts.size) { 0 }

        for (i in 0 until maxLength) {
            if (paddedInstalled[i] < paddedMinimum[i]) return true
            if (paddedInstalled[i] > paddedMinimum[i]) return false
        }

        return false // they are equal
    }
}
