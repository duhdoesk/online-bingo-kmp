package ui.feature.profile.component.picture

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.ic_edit
import ui.feature.core.buttons.CustomIconButton
import ui.theme.profileOnColor
import ui.theme.profilePrimaryColor

@Composable
fun ProfileScreenUserPicture(
    pictureUri: String,
    onPictureChange: () -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
                .shadow(3.dp, RoundedCornerShape(12.dp))
                .border(4.dp, profileOnColor, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.Center)
                .size(120.dp)
        ) {
            AsyncImage(
                model = pictureUri,
                placeholder = painterResource(Res.drawable.hot_water_logo),
                error = painterResource(Res.drawable.hot_water_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        CustomIconButton(
            icon = painterResource(Res.drawable.ic_edit),
            colors = ButtonDefaults.buttonColors(
                containerColor = profilePrimaryColor,
                contentColor = profileOnColor
            ),
            onClick = onPictureChange,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
