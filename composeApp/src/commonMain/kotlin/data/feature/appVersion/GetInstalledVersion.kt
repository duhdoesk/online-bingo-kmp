package data.feature.appVersion

import domain.util.resource.Resource

expect fun getPlatformInstalledVersion(): Resource<String>
