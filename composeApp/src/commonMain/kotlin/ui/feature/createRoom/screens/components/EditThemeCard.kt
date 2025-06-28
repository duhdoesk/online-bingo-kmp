package ui.feature.createRoom.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import domain.theme.model.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.ic_edit
import themedbingo.composeapp.generated.resources.theme_card
import ui.theme.LuckiestGuyFontFamily
import ui.theme.PoppinsFontFamily

@Composable
fun EditThemeCard(
    selectedTheme: Theme?,
    onThemeSelected: (Theme) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditThemeCardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showThemesBottomSheet by remember { mutableStateOf(false) }

    EditThemesCardContent(
        uiState = uiState,
        selectedTheme = selectedTheme,
        onShowBottomSheet = { showThemesBottomSheet = true },
        modifier = modifier
    )

    if (showThemesBottomSheet) {
    }
}

@Composable
private fun EditThemesCardContent(
    selectedTheme: Theme?,
    uiState: EditThemeCardUiState = EditThemeCardUiState(),
    onShowBottomSheet: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var columnSize by remember { mutableStateOf(Size.Zero) }

    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = { if (!uiState.isLoading) onShowBottomSheet },
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = selectedTheme?.pictureUri,
                placeholder = painterResource(Res.drawable.hot_water_logo),
                error = painterResource(Res.drawable.hot_water_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                clipToBounds = true,
                modifier = Modifier
                    .size(with(LocalDensity.current) { columnSize.height.toDp() + 32.dp })
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
                    .weight(1f)
                    .onGloballyPositioned {
                        columnSize = it.size.toSize()
                    }
            ) {
                Text(
                    text = stringResource(Res.string.theme_card),
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily()
                )

                Text(
                    text = selectedTheme?.name.orEmpty(),
                    fontSize = 20.sp,
                    fontFamily = LuckiestGuyFontFamily(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(20.dp)
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(20.dp)
                )
            }
        }
    }
}
