@file:OptIn(ExperimentalMaterial3Api::class)

package ui.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_splash
import ui.presentation.core.buttons.CustomIconButton
import ui.presentation.core.text.OutlinedShadowedText
import ui.theme.AppTheme

@Composable
fun CustomTopBar(
    text: String,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    onPrimaryColor: Color = MaterialTheme.colorScheme.onPrimary,
    onBackPressed: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomIconButton(
            onClick = onBackPressed,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = primaryColor,
                contentColor = onPrimaryColor
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
        )

        OutlinedShadowedText(
            text = text,
            fontSize = 32,
            strokeWidth = 2.dp.value,
            fontColor = onPrimaryColor,
            strokeColor = primaryColor,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun Preview() {
    AppTheme {
        Box {
            Image(
                painter = painterResource(Res.drawable.bg_splash),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Scaffold(
            topBar = {
                CustomTopBar(
                    text = "Bingo\nTemático",
                    onBackPressed = {}
                )
            },
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) {
        }
    }
}
