package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import domain.room.model.BingoType
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_beach_volley
import themedbingo.composeapp.generated.resources.bg_forest_sunlight

private val colorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    tertiary = tertiary,
    onTertiary = onTertiary,
    tertiaryContainer = tertiaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = PoppinsTypography(),
        content = content
    )
}

@Composable
fun BingoTypeTheme(
    content: @Composable () -> Unit,
    type: BingoType
) {
    when (type) {
        BingoType.THEMED -> {
            MaterialTheme(
                colorScheme = colorScheme.copy(
                    primary = themedBingoPrimaryColor,
                    onPrimary = themedBingoOnPrimaryColor,
                    secondary = themedBingoPrimaryColor,
                    onSecondary = themedBingoOnPrimaryColor
                ),
                typography = PoppinsTypography(),
                content = content
            )
        }
        BingoType.CLASSIC -> {
            MaterialTheme(
                colorScheme = colorScheme.copy(
                    primary = classicBingoPrimaryColor,
                    onPrimary = classicBingoOnPrimaryColor,
                    secondary = classicBingoPrimaryColor,
                    onSecondary = classicBingoOnPrimaryColor
                ),
                typography = PoppinsTypography(),
                content = content
            )
        }
    }
}

@Composable
fun GetBingoTypeBackground(
    type: BingoType
): Painter {
    val resource = when (type) {
        BingoType.THEMED -> Res.drawable.bg_forest_sunlight
        BingoType.CLASSIC -> Res.drawable.bg_beach_volley
    }
    return painterResource(resource)
}
