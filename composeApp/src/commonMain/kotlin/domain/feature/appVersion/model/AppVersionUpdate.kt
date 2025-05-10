package domain.feature.appVersion.model

sealed class AppVersionUpdate {
    data class UpdateRequired(val updateUrl: String) : AppVersionUpdate()
    object NoUpdateRequired : AppVersionUpdate()
}
