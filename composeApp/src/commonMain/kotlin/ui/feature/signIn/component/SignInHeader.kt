package ui.feature.signIn.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.hot_water_software
import themedbingo.composeapp.generated.resources.mobile_apps_development

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInHeader(
    modifier: Modifier = Modifier,
    maxPictureWidth: Dp = 240.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                resource = Res.drawable.hot_water_logo
            ),
            contentDescription = "Hot Water Software Logo",
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .sizeIn(
                    minWidth = 160.dp,
                    maxWidth = maxPictureWidth
                )
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(resource = Res.string.hot_water_software),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(resource = Res.string.mobile_apps_development),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        )
    }
}
