@file:OptIn(ExperimentalMaterial3Api::class)

package ui.feature.profile.component.picture

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import domain.profilePictures.model.ProfilePictures
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.confirm_button
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.profile_change_picture_title
import ui.feature.core.buttons.QuitAndPrimaryButtonRow
import ui.feature.core.text.OutlinedShadowedText
import ui.theme.error
import ui.theme.homeOnColor
import ui.theme.homePrimaryColor

@Composable
fun ChangePictureBottomSheet(
    currentPicture: String,
    onCancel: () -> Unit,
    onPictureChange: (String) -> Unit,
    sheetState: SheetState,
    viewModel: ChangePictureViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedPicture by remember { mutableStateOf(currentPicture) }
    val confirmEnabled by derivedStateOf { currentPicture != selectedPicture }

    ChangePictureBottomSheet(
        selectedPicture = selectedPicture,
        uiState = uiState,
        onCancel = onCancel,
        onChangeSelection = { selectedPicture = it },
        onPictureChange = { onPictureChange(selectedPicture) },
        onSelectCategory = { viewModel.onUiEvent(ChangePictureUiEvent.SelectCategory(it)) },
        sheetState = sheetState,
        confirmEnabled = confirmEnabled
    )
}

@Composable
private fun ChangePictureBottomSheet(
    selectedPicture: String,
    uiState: ChangePictureUiState,
    onCancel: () -> Unit,
    onChangeSelection: (String) -> Unit,
    onPictureChange: () -> Unit,
    onSelectCategory: (ProfilePictures.Category) -> Unit,
    sheetState: SheetState,
    confirmEnabled: Boolean
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        ModalBottomSheet(
            onDismissRequest = onCancel,
            sheetState = sheetState,
            sheetMaxWidth = 600.dp,
            modifier = Modifier
                .heightIn(max = maxHeight * 0.94f)
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp))
                    .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = selectedPicture,
                    placeholder = painterResource(Res.drawable.hot_water_logo),
                    error = painterResource(Res.drawable.hot_water_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            OutlinedShadowedText(
                text = stringResource(Res.string.profile_change_picture_title),
                fontSize = 20,
                strokeWidth = 3f,
                fontColor = homePrimaryColor,
                strokeColor = homeOnColor,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            CategoriesChips(
                categories = uiState.allCategories,
                selectedCategories = uiState.selectedCategories,
                onSelectCategory = onSelectCategory,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            AvailablePicturesHorizontalGrid(
                availablePictures = uiState.availablePictures,
                selectedPicture = selectedPicture,
                onPictureClick = onChangeSelection,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            QuitAndPrimaryButtonRow(
                primaryButtonColors = ButtonDefaults.buttonColors(
                    containerColor = homePrimaryColor,
                    contentColor = homeOnColor
                ),
                iconButtonColors = ButtonDefaults.buttonColors(
                    containerColor = homeOnColor,
                    contentColor = error
                ),
                primaryText = stringResource(Res.string.confirm_button),
                primaryEnabled = confirmEnabled,
                quitEnabled = true,
                onPrimaryClick = onPictureChange,
                onQuitClick = onCancel,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}
