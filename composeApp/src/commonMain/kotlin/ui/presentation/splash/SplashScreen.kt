package ui.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_splash
import themedbingo.composeapp.generated.resources.getting_things_done
import ui.presentation.splash.component.LoadingBar
import ui.presentation.util.OutlinedShadowedText
import ui.theme.AppTheme

@Composable
fun SplashScreen(component: SplashScreenComponent) {
    val progress by component.progress.collectAsStateWithLifecycle()

    SplashScreen(
        progress = progress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SplashScreen(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column {
            OutlinedShadowedText(
                text = stringResource(Res.string.getting_things_done),
                fontSize = 36,
                strokeWidth = 3f,
                fontColor = Color(0xFFFF5045),
                strokeColor = Color.White,
                modifier = Modifier.width(200.dp)
            )

            Spacer(Modifier.height(20.dp))

            LoadingBar(
                progress = progress,
                modifier = Modifier.width(200.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SplashPreview() {
    AppTheme {
        SplashScreen(
            progress = 0.4f,
            modifier = Modifier.fillMaxSize()
        )
    }
}
