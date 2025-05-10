package data.feature.appVersion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppVersionDto(
    val platform: String,
    @SerialName("min_supported_version") val minSupportedVersion: String,
    @SerialName("update_url") val updateUrl: String
)
