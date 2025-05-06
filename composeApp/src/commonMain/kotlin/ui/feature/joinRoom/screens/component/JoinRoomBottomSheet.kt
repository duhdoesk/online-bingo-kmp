package ui.feature.joinRoom.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import domain.room.model.BingoRoom
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.cancel_button
import themedbingo.composeapp.generated.resources.confirm_button
import themedbingo.composeapp.generated.resources.join_locked_room_body
import themedbingo.composeapp.generated.resources.join_unlocked_room_body
import themedbingo.composeapp.generated.resources.password

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JoinRoomBottomSheet(
    room: BingoRoom,
    onDismiss: () -> Unit,
    onConfirm: (password: String?) -> Unit
) {
    var password by remember { mutableStateOf<String?>(null) }
    val keyboardManager = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets.ime }
    ) {
        Column {
//            Text(
//                text = stringResource(Res.string.join_room),
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
//            )

            Spacer(Modifier.height(4.dp))

            if (room.locked) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(Res.string.join_locked_room_body))
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(" ${room.name}") }
                        append(".")
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedTextField(
                    label = { Text(stringResource(Res.string.password)) },
                    value = password ?: "",
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onConfirm(password)
                        keyboardManager?.hide()
                    }),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            } else {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(Res.string.join_unlocked_room_body))
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(" ${room.name}") }
                        append("?")
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth()
            ) {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(Res.string.cancel_button))
                }

                TextButton(onClick = { onConfirm(password) }) {
                    Text(stringResource(Res.string.confirm_button))
                }
            }

            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}
