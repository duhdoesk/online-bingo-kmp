@file:OptIn(ExperimentalResourceApi::class)

package domain.room.model

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.not_started

enum class RoomState(val description: StringResource) {
    NOT_STARTED(Res.string.not_started),
    RUNNING(Res.string.not_started),
    FINISHED(Res.string.not_started)
}
