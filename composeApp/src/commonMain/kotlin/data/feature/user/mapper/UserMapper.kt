package data.feature.user.mapper

import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.toMilliseconds
import domain.billing.hasActiveEntitlements
import domain.feature.user.model.Tier
import domain.feature.user.model.User
import domain.feature.user.model.getLocalizedMessage
import domain.feature.user.model.getLocalizedName
import domain.feature.user.model.getRandomPictureUri
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import util.getLocalDateTimeNow

suspend fun DocumentSnapshot.toUserModel(): User {
    val createdAt = (this.get("createdAt") as? Timestamp)?.let {
        Instant.fromEpochMilliseconds(it.toMilliseconds().toLong()).toLocalDateTime(TimeZone.UTC)
    } ?: getLocalDateTimeNow()

    val updatedAt = (this.get("updatedAt") as? Timestamp)?.let {
        Instant.fromEpochMilliseconds(it.toMilliseconds().toLong()).toLocalDateTime(TimeZone.UTC)
    }

    val tier = if (hasActiveEntitlements()) Tier.VIP else Tier.FREE

    return User(
        id = this.id,
        createdAt = createdAt,
        email = this.get("email") as? String? ?: "",
        name = this.get("name") as? String? ?: getLocalizedName(),
        pictureUri = this.get("pictureUri") as? String? ?: getRandomPictureUri(),
        victoryMessage = this.get("victoryMessage") as? String? ?: getLocalizedMessage(),
        updatedAt = updatedAt,
        tier = tier
    )
}
