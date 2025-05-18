package ui.feature.profile.component.picture

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.profilePictures.ProfilePictures
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import ui.feature.core.text.OutlinedText
import ui.theme.homeOnColor
import ui.theme.homeSecondaryColor

@Composable
fun EditProfilePictureCategory(
    category: ProfilePictures.Category,
    selectedPictureUrl: String?,
    onItemClick: (pictureUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedText(
            text = category.name.capitalize(Locale.current),
            fontSize = 20,
            fontColor = homeOnColor,
            strokeWidth = 3f,
            strokeColor = homeSecondaryColor,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(category.pictures) { pictureUrl ->
                EditProfilePicturePictureItem(
                    pictureUrl = pictureUrl,
                    isSelected = pictureUrl == selectedPictureUrl,
                    onItemClick = { onItemClick(pictureUrl) }
                )
            }
        }
    }
}

@Composable
private fun EditProfilePicturePictureItem(
    pictureUrl: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val borderModifier = if (isSelected) {
        Modifier.border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
    } else {
        Modifier
    }

    Box(
        modifier = borderModifier
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .size(80.dp)
            .clickable { onItemClick() }
            .background(homeSecondaryColor)
    ) {
        AsyncImage(
            model = pictureUrl,
            placeholder = painterResource(Res.drawable.hot_water_logo),
            error = painterResource(Res.drawable.hot_water_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
