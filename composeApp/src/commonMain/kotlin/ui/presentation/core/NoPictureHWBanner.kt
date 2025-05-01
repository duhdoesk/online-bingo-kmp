package ui.presentation.core

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_software
import themedbingo.composeapp.generated.resources.mobile_apps_development

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NoPictureHWBanner(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(resource = Res.string.hot_water_software),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = stringResource(resource = Res.string.mobile_apps_development),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        )
    }
}
