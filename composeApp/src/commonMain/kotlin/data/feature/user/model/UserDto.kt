package data.feature.user.model

import domain.feature.user.model.Tier
import domain.feature.user.model.User
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?,
    val name: String,
    val message: String,
    @SerialName("picture_url") val pictureUrl: String,
    val tier: String
) {
    fun toModel(): User {
        return User(
            id = this.id,
            email = this.email,
            createdAt = Instant.parse(this.createdAt).toLocalDateTime(TimeZone.UTC),
            updatedAt = this.updatedAt?.let { Instant.parse(it).toLocalDateTime(TimeZone.UTC) },
            name = this.name,
            victoryMessage = this.message,
            pictureUri = this.pictureUrl,
            tier = Tier.entries.find { it.name == this.tier.uppercase() } ?: Tier.FREE
        )
    }
}
