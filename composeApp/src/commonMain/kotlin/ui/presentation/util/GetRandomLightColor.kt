package ui.presentation.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun getRandomLightColor() =
    Color(
        blue = Random.nextInt(180, 256),
        red = Random.nextInt(180, 256),
        green = Random.nextInt(180, 256),
        alpha = 255
    )