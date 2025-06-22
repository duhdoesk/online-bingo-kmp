package ui.feature.lobby.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_close
import themedbingo.composeapp.generated.resources.ic_search
import themedbingo.composeapp.generated.resources.search
import ui.theme.AppTheme
import ui.theme.lobbySecondaryColor

@Composable
fun SearchTextField(
    currentValue: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(TextFieldValue(currentValue)) }
    LaunchedEffect(query) { onValueChange(query.text) }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        singleLine = true,
        placeholder = {
            Text(text = stringResource(Res.string.search))
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        trailingIcon = {
            if (currentValue.isNotEmpty()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .clickable { query = TextFieldValue(""); onDone() }
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = lobbySecondaryColor,
            unfocusedBorderColor = lobbySecondaryColor,
            focusedTrailingIconColor = lobbySecondaryColor,
            unfocusedTrailingIconColor = lobbySecondaryColor,
            focusedTextColor = lobbySecondaryColor,
            unfocusedTextColor = lobbySecondaryColor,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedPlaceholderColor = lobbySecondaryColor,
            unfocusedPlaceholderColor = lobbySecondaryColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(10.dp)
            )
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            SearchTextField(
                currentValue = "",
                onValueChange = {},
                onDone = {},
                modifier = Modifier
                    .padding(20.dp)
                    .height(56.dp)
                    .fillMaxWidth()
            )
        }
    }
}
