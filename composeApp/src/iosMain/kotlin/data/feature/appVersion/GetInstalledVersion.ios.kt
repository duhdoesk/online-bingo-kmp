package data.feature.appVersion

import domain.util.resource.Cause
import domain.util.resource.Resource
import platform.Foundation.NSBundle

actual fun getPlatformInstalledVersion(): Resource<String> {
    val version = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
    if (version == null) { return Resource.Failure(Cause.UNKNOWN) }
    return Resource.Success(version)
}
