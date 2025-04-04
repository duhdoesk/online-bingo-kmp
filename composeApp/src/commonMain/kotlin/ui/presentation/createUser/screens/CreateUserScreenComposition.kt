package ui.presentation.createUser.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.create_user_screen_body
import themedbingo.composeapp.generated.resources.create_user_screen_title
import themedbingo.composeapp.generated.resources.edit_button
import themedbingo.composeapp.generated.resources.exit_button
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.user_avatar
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.createUser.event.CreateUserEvent
import ui.presentation.createUser.state.CreateUserState
import ui.presentation.profile.screens.component.ProfileScreenStringDataSection
import ui.presentation.util.bottomSheet.UpdateBottomSheet
import ui.presentation.util.bottomSheet.UpdatePictureBottomSheet
import ui.presentation.util.dialog.GenericActionDialog

@ExperimentalResourceApi
@ExperimentalMaterial3Api
@Composable
fun CreateUserScreenComposition(
    screenState: CreateUserState,
    event: (event: CreateUserEvent) -> Unit
) {
    /**
     * Coroutine Scope
     */
    val coroutineScope = rememberCoroutineScope()

    /**
     * Update Bottom Sheets visibility holders
     */
    var updateNameVisible by remember { mutableStateOf(false) }
    val updateNameState = rememberModalBottomSheetState(true)

    var updateMessageVisible by remember { mutableStateOf(false) }
    val updateMessageState = rememberModalBottomSheetState(true)

    var updatePictureVisible by remember { mutableStateOf(false) }
    val updatePictureState = rememberModalBottomSheetState(true)

    var exitConfirmationVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.create_user_screen_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(Res.string.create_user_screen_body),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            AsyncImage(
                model = screenState.pictureUri,
                contentDescription = stringResource(Res.string.user_avatar),
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                clipToBounds = true
            )

            IconButton(
                onClick = { updatePictureVisible = true },
                enabled = true,
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                content = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(Res.string.edit_button)
                    )
                }
            )
        }

        Spacer(Modifier.height(40.dp))

        ProfileScreenStringDataSection(
            label = Res.string.nickname,
            data = screenState.name,
            lastEditTimestamp = Timestamp.now(),
            editable = true,
            onEdit = { updateNameVisible = true },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(48.dp))

        ProfileScreenStringDataSection(
            label = Res.string.victory_message,
            data = screenState.message,
            lastEditTimestamp = Timestamp.now(),
            editable = true,
            onEdit = { updateMessageVisible = true },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = { event(CreateUserEvent.CreateUser) },
            modifier = Modifier.width(200.dp),
            enabled = screenState.canProceed
        ) {
            Text(stringResource(Res.string.create_button))
        }

        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = { exitConfirmationVisible = true },
            modifier = Modifier.width(200.dp),
            enabled = true
        ) {
            Text(stringResource(Res.string.exit_button))
        }
    }

    /**
     * Visibility of dialogs and bottom sheets
     */
    if (updateNameVisible) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { updateNameVisible = false } },
            onConfirm = {
                coroutineScope.launch {
                    updateNameState.hide()
                    updateNameVisible = false
                    event(CreateUserEvent.UpdateName(it))
                }
            },
            currentData = screenState.name,
            title = Res.string.update_nickname_title,
            body = Res.string.update_nickname_body,
            label = Res.string.nickname,
            sheetState = updateNameState
        )
    }

    if (updateMessageVisible) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { updateMessageVisible = false } },
            onConfirm = {
                coroutineScope.launch {
                    updateMessageState.hide()
                    updateMessageVisible = false
                    event(CreateUserEvent.UpdateVictoryMessage(it))
                }
            },
            currentData = screenState.message,
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
            sheetState = updateMessageState
        )
    }

    if (updatePictureVisible) {
        UpdatePictureBottomSheet(
            sheetState = updatePictureState,
            currentName = screenState.name,
            currentPicture = screenState.pictureUri,
            availablePictures = screenState.profilePictures,
            onUpdatePicture = { event(CreateUserEvent.UpdatePicture(it)) },
            onHide = {
                coroutineScope.launch {
                    updatePictureState.hide()
                    updatePictureVisible = false
                }
            }
        )
    }

    if (exitConfirmationVisible) {
        GenericActionDialog(
            onDismiss = { exitConfirmationVisible = false },
            onConfirm = {
                exitConfirmationVisible = false
                event(CreateUserEvent.SignOut)
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body
        )
    }
}
