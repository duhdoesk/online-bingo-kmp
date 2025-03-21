package ui.presentation.util.bottomSheet

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.user.useCase.ProfilePictures
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.user_avatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePictureBottomSheet(
    sheetState: SheetState,
    currentName: String,
    currentPicture: String,
    availablePictures: ProfilePictures?,
    onUpdatePicture: (pictureUri: String) -> Unit,
    onHide: () -> Unit
) {
    var updatedPicture by remember { mutableStateOf(currentPicture) }

    ModalBottomSheet(
        onDismissRequest = { onHide() },
        sheetState = sheetState,
        windowInsets = WindowInsets.ime
    ) {
        Column {
            EditProfilePictureCurrentUserData(
                name = currentName,
                avatarUrl = updatedPicture
            )

            if (availablePictures != null) {
                EditProfilePicturePictures(
                    pictures = availablePictures,
                    selectedPictureUrl = updatedPicture,
                    onItemClick = { updatedPicture = it },
                    modifier = Modifier.weight(1f)
                )
            }

            BottomSheetButtons(
                onCancel = { onHide() },
                onConfirm = {
                    onHide()
                    onUpdatePicture(updatedPicture)
                },
                canConfirm = (currentPicture != updatedPicture),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EditProfilePictureCurrentUserData(
    name: String?,
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = stringResource(Res.string.user_avatar),
            modifier = Modifier
                .fillMaxSize()
                .blur(40.dp)
                .alpha(0.7f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = stringResource(Res.string.user_avatar),
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                clipToBounds = true
            )

            Text(
                text = name.orEmpty(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EditProfilePicturePictures(
    pictures: ProfilePictures,
    selectedPictureUrl: String?,
    onItemClick: (pictureUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp)
    ) {
        items(pictures.categories) { category ->
            EditProfilePicturePictureCategory(
                category = category,
                selectedPictureUrl = selectedPictureUrl,
                onItemClick = onItemClick,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun EditProfilePicturePictureCategory(
    category: ProfilePictures.Category,
    selectedPictureUrl: String?,
    onItemClick: (pictureUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = category.name.capitalize(Locale.current),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
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
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun EditProfilePicturePictureItem(
    pictureUrl: String,
    isSelected: Boolean,
    onItemClick: (pictureUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderModifier = if (isSelected) {
        Modifier.border(
            width = 2.dp,
            color = Color.Red,
            shape = CircleShape
        )
    } else {
        Modifier
    }

    AsyncImage(
        model = pictureUrl,
        contentDescription = "Picture",
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .then(borderModifier)
            .clickable { onItemClick(pictureUrl) },
        contentScale = ContentScale.Crop,
        clipToBounds = true
    )
}
