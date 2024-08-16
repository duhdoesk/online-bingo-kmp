package ui.presentation.create_room.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.theme_picture
import themedbingo.composeapp.generated.resources.theme_textField
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomThemePicker(
    modifier: Modifier = Modifier,
    uiState: CreateScreenUiState,
    leadingIconModifier: Modifier,
    contentScale: ContentScale,
    onUpdateThemeId: (themeId: String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        var expanded by remember { mutableStateOf(false) }
        val selectedTheme = uiState.availableThemes.find { it.id == uiState.themeId }
        val color by remember { mutableStateOf(getRandomLightColor()) }

        if (selectedTheme != null) {
            AsyncImage(
                model = selectedTheme.pictureUri,
                contentDescription = null,
                modifier = leadingIconModifier,
                contentScale = contentScale
            )
        } else {
            Box(modifier = leadingIconModifier) {
                Surface(
                    color = color,
                    modifier = Modifier
                        .fillMaxSize()
                ) {}

                Text(
                    text = "?",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = selectedTheme?.name ?: "",
                label = { Text(text = stringResource(resource = Res.string.theme_textField)) },
                readOnly = true,
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                uiState.availableThemes.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        leadingIcon = {
                            AsyncImage(
                                model = item.pictureUri,
                                contentDescription = stringResource(resource = Res.string.theme_picture),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                contentScale = ContentScale.Crop
                            )
                        },
                        onClick = {
                            onUpdateThemeId(item.id)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}