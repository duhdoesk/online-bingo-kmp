package ui.feature.createUser.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.user_avatar
import ui.feature.core.buttons.CustomIconButton
import ui.theme.createUserOnColor
import ui.theme.createUserSecondaryColor

@Composable
fun UserPictureBox(
    pictureUri: String? = null,
    onEditPicture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = pictureUri,
            placeholder = painterResource(Res.drawable.hot_water_logo),
            error = painterResource(Res.drawable.hot_water_logo),
            contentDescription = stringResource(Res.string.user_avatar),
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            modifier = Modifier
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                )
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(4.dp, createUserOnColor, RoundedCornerShape(16.dp))
        )

        CustomIconButton(
            onClick = onEditPicture,
            modifier = Modifier
                .align(Alignment.BottomEnd),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = createUserSecondaryColor,
                contentColor = createUserOnColor
            ),
            icon = Icons.Default.Edit
        )
    }
}
