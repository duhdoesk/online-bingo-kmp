package ui.feature.createRoom.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_abc_24
import themedbingo.composeapp.generated.resources.name_textField
import ui.feature.createRoom.CreateRoomUiState
import ui.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomName(
    modifier: Modifier = Modifier,
    uiState: CreateRoomUiState,
    leadingIconModifier: Modifier,
    onUpdateName: (name: String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        val color by remember { mutableStateOf(getRandomLightColor()) }

        Box(modifier = leadingIconModifier) {
            Surface(
                color = color,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = vectorResource(resource = Res.drawable.baseline_abc_24),
                    tint = Color.Black,
                    contentDescription = "ABC",
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = uiState.name,
                label = { Text(text = stringResource(resource = Res.string.name_textField)) },
                onValueChange = { name ->
                    onUpdateName(name)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
//        for (error in uiState.nameErrors) {
//            Text(
//                text = stringResource(error),
//                color = MaterialTheme.colorScheme.error
//            )
//        }
    }
}
