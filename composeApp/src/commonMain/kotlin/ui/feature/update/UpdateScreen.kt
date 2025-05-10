package ui.feature.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_antarctica_penguins
import themedbingo.composeapp.generated.resources.update_screen_body
import themedbingo.composeapp.generated.resources.update_screen_button
import themedbingo.composeapp.generated.resources.update_screen_title
import ui.feature.core.buttons.CustomPrimaryButton
import ui.feature.core.text.OutlinedShadowedText
import ui.theme.AppTheme

@Composable
fun UpdateScreen(component: UpdateScreenComponent) {
    val updateUrl = component.updateUrl
    val uriHandler = LocalUriHandler.current

    UpdateScreen(
        onGoToStore = { uriHandler.openUri(updateUrl) }
    )
}

@Composable
private fun UpdateScreen(
    onGoToStore: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box {
            Image(
                painter = painterResource(Res.drawable.bg_antarctica_penguins),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .widthIn(max = 600.dp)
                    .align(Alignment.Center)
            ) {
                OutlinedShadowedText(
                    text = stringResource(Res.string.update_screen_title),
                    textAlign = TextAlign.Center,
                    fontSize = 24,
                    strokeWidth = 3f,
                    fontColor = Color.White,
                    strokeColor = Color.Blue,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = stringResource(Res.string.update_screen_body),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(32.dp))

                CustomPrimaryButton(
                    text = stringResource(Res.string.update_screen_button),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = onGoToStore
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        UpdateScreen(
            onGoToStore = {}
        )
    }
}
