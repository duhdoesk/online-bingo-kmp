package ui.feature.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.hot_water_logo),
            contentDescription = "Hot Water Logo",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

//        Column {
//            Text(
//                text = stringResource(resource = Res.string.hot_water_software),
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Text(
//                text = stringResource(resource = Res.string.mobile_apps_development),
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.Light,
//                fontStyle = FontStyle.Italic
//            )
//        }
    }
}
