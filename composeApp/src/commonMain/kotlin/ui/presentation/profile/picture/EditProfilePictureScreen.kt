package ui.presentation.profile.picture

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.user.use_case.ProfilePictures
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.confirm_button
import themedbingo.composeapp.generated.resources.user_avatar
import ui.presentation.common.BottomButtonRow

@Composable
fun EditProfilePictureScreen(
    component: EditProfilePictureScreenComponent,
) {
    val state by component.uiState.collectAsState()

    LaunchedEffect(Unit) {
        component.onEvent(EditProfilePictureUiEvent.OnUiLoaded)
    }

    EditProfilePictureContent(
        state = state,
        onEvent = component::onEvent
    )
}

@Composable
private fun EditProfilePictureContent(
    state: EditProfilePictureUiState,
    onEvent: (event: EditProfilePictureUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { padding ->
        when {
            state.loading -> EditProfilePictureLoadingState(
                modifier = Modifier
                    .padding(padding)
            )

            state.pictures != null -> EditProfilePictureLoadedState(
                currentPictureUrl = state.currentPictureUrl,
                userName = state.userName,
                selectedPictureUrl = state.selectedPictureUrl,
                pictures = state.pictures,
                onItemClick = { pictureUrl ->
                    onEvent(EditProfilePictureUiEvent.OnPictureSelected(pictureUrl))
                },
                onCancel = {
                    onEvent(EditProfilePictureUiEvent.OnCancel)
                },
                onConfirm = {
                    onEvent(EditProfilePictureUiEvent.OnConfirm)
                },
                modifier = Modifier
                    .padding(padding)
            )
        }
    }
}

@Composable
private fun EditProfilePictureLoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EditProfilePictureLoadedState(
    currentPictureUrl: String?,
    userName: String?,
    selectedPictureUrl: String?,
    pictures: ProfilePictures,
    onItemClick: (pictureUrl: String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        EditProfilePictureCurrentUserData(
            avatarUrl = currentPictureUrl,
            name = userName,
        )
        EditProfilePicturePictures(
            pictures = pictures,
            selectedPictureUrl = selectedPictureUrl,
            onItemClick = onItemClick,
            modifier = Modifier.weight(1f)
        )
        BottomButtonRow(
            rightEnabled = selectedPictureUrl != null,
            rightText = Res.string.confirm_button,
            leftClicked = onCancel,
            rightClicked = onConfirm
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EditProfilePictureCurrentUserData(
    name: String?,
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                color = Color.Cyan
            ),
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
            clipToBounds = true,
        )

        Text(name.orEmpty())
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
        modifier = modifier
    ) {
        items(pictures.categories) { category ->
            EditProfilePicturePictureCategory(
                category = category,
                selectedPictureUrl = selectedPictureUrl,
                onItemClick = onItemClick
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
        Text(category.name.capitalize(Locale.current))
        LazyRow {
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
            .size(80.dp)
            .clip(CircleShape)
            .then(borderModifier)
            .clickable { onItemClick(pictureUrl) },
        contentScale = ContentScale.Crop,
        clipToBounds = true
    )

}