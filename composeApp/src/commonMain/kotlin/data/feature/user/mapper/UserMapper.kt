package data.feature.user.mapper

import dev.gitlive.firebase.firestore.DocumentSnapshot
import domain.feature.user.model.Tier
import domain.feature.user.model.User
import domain.feature.user.model.getLocalizedMessage
import domain.feature.user.model.getLocalizedName
import domain.feature.user.model.getRandomPictureUri
import util.getDateTimeZero

fun DocumentSnapshot.toUserModel(): User {
    return User(
        id = this.id,
        createdAt = this.get("createdAt") ?: getDateTimeZero(),
        email = this.get("email") ?: "",
        name = this.get("name") ?: getLocalizedName(),
        pictureUri = this.get("pictureUri") ?: getRandomPictureUri(),
        victoryMessage = this.get("victoryMessage") ?: getLocalizedMessage(),
        updatedAt = this.get("updatedAt") ?: getDateTimeZero(),
        tier = Tier.entries.find { it.name == this.get("tier") } ?: Tier.FREE
    )
}
