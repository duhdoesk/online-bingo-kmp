package ui.feature.profile.component.picture

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import ui.theme.homeOnColor
import ui.theme.homePrimaryColor

@Composable
fun AvailablePicturesHorizontalGrid(
    availablePictures: List<String>,
    selectedPicture: String,
    onPictureClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(availablePictures) {
        gridState.scrollToItem(0)
    }

    LazyHorizontalGrid(
        rows = GridCells.Adaptive(minSize = 100.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = gridState,
        modifier = modifier
    ) {
        items(availablePictures) { picture ->
            val borderModifier = if (picture == selectedPicture) {
                Modifier.border(4.dp, homePrimaryColor, RoundedCornerShape(12.dp))
            } else {
                Modifier
            }

            Box(
                modifier = borderModifier
                    .shadow(3.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPictureClick(picture) }
                    .background(homeOnColor)
                    .aspectRatio(0.7f)
            ) {
                AsyncImage(
                    model = picture,
                    placeholder = painterResource(Res.drawable.hot_water_logo),
                    error = painterResource(Res.drawable.hot_water_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}
