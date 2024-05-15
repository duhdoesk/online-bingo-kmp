package ui.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(
    screenHeight: Dp,
    screenWidth: Dp
): WindowInfo {

    val deviceOrientation =
        if (screenHeight > screenWidth) WindowInfo.DeviceOrientation.Portrait
        else WindowInfo.DeviceOrientation.Landscape

    return WindowInfo(
        screenWidthInfo = when {
            screenWidth < 600.dp -> WindowInfo.WindowType.Compact
            screenWidth < 840.dp -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },

        screenHeightInfo = when {
            screenHeight < 480.dp -> WindowInfo.WindowType.Compact
            screenHeight < 900.dp -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },

        screenWidth = screenWidth,
        screenHeight = screenHeight,
        screenOrientation = deviceOrientation
    )
}

data class WindowInfo(
    val screenOrientation: DeviceOrientation,
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {

    sealed class WindowType {
        data object Compact : WindowType()
        data object Medium : WindowType()
        data object Expanded : WindowType()
    }

    sealed class DeviceOrientation {
        data object Portrait: DeviceOrientation()
        data object Landscape: DeviceOrientation()
    }
}


