package ui.feature.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_screen_rotation_24
import themedbingo.composeapp.generated.resources.rotate_your_device
import themedbingo.composeapp.generated.resources.screen_rotation

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RotateScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.baseline_screen_rotation_24),
            contentDescription = stringResource(Res.string.screen_rotation)
        )
        Text(stringResource(Res.string.rotate_your_device))
    }
}
