package ui.feature.room.screens.component

import androidx.compose.ui.graphics.Color

fun getBackgroundColorByNumber(number: Int): Color {
    return when (number) {
        in 1..15 -> Color(0, 115, 230, 30)
        in 16..30 -> Color(115, 0, 230, 30)
        in 31..45 -> Color(230, 230, 0, 30)
        in 46..60 -> Color(0, 230, 0, 30)
        else -> Color(230, 0, 0, 30)
    }
}
