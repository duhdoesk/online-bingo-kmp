package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import domain.room.model.BingoType

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
fun CreateRoomTheme(
    content: @Composable () -> Unit,
    type: BingoType
) {
    when (type) {
        BingoType.THEMED -> {
            MaterialTheme(
                colorScheme = colorScheme.copy(
                    primary = createRoomThemedColor,
                    onPrimary = createRoomThemedOnColor,
                    secondary = createRoomThemedColor,
                    onSecondary = createRoomThemedOnColor
                ),
                typography = PoppinsTypography(),
                content = content
            )
        }
        BingoType.CLASSIC -> {
            MaterialTheme(
                colorScheme = colorScheme.copy(
                    primary = createRoomClassicColor,
                    onPrimary = createRoomClassicOnColor,
                    secondary = createRoomClassicColor,
                    onSecondary = createRoomClassicOnColor
                ),
                typography = PoppinsTypography(),
                content = content
            )
        }
    }
}
