package data.feature.appVersion

import android.content.Context
import android.content.pm.PackageManager
import domain.util.resource.Cause
import domain.util.resource.Resource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidAppVersionProvider : KoinComponent {
    private val appContext: Context by inject()

    fun getVersion(): Resource<String> {
        return try {
            val packageInfo = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
            Resource.Success(packageInfo.versionName.toString())
        } catch (e: PackageManager.NameNotFoundException) {
            Resource.Failure(Cause.UNKNOWN)
        }
    }
}

actual fun getPlatformInstalledVersion(): Resource<String> {
    return AndroidAppVersionProvider().getVersion()
}
