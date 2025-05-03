package data.feature.user.mapper

import dev.gitlive.firebase.firestore.DocumentSnapshot
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
        lastWinAt = this.get("lastWinAt") ?: getDateTimeZero(),
        nameUpdatedAt = this.get("nameUpdatedAt") ?: getDateTimeZero(),
        pictureUriUpdatedAt = this.get("pictureUriUpdatedAt") ?: getDateTimeZero(),
        victoryMessageUpdatedAt = this.get("victoryMessageUpdatedAt") ?: getDateTimeZero()
    )
}
