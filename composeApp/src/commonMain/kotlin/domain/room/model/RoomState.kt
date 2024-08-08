@file:OptIn(ExperimentalResourceApi::class)

package domain.room.model

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.user_avatar

enum class RoomState (val description: StringResource) {
    //todo(): string resources for each
    NOT_STARTED(Res.string.user_avatar),
    RUNNING(Res.string.user_avatar),
    FINISHED(Res.string.user_avatar),
}