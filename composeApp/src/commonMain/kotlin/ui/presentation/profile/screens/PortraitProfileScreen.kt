package ui.presentation.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.profilePictures.ProfilePictures
import domain.user.model.User
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.delete_account_body
import themedbingo.composeapp.generated.resources.delete_account_title
import themedbingo.composeapp.generated.resources.edit_button
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.user_avatar
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.core.SingleButtonRow
import ui.presentation.core.bottomSheet.UpdateBottomSheet
import ui.presentation.core.bottomSheet.UpdatePictureBottomSheet
import ui.presentation.core.dialog.GenericActionDialog
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.component.ProfileScreenListDataSection
import ui.presentation.profile.screens.component.ProfileScreenStringDataSection

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PortraitProfileScreen(
    user: User,
    profilePictures: ProfilePictures?,
    event: (event: ProfileScreenEvent) -> Unit
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

    var signOutVisible by remember { mutableStateOf(false) }
    var deleteAccountVisible by remember { mutableStateOf(false) }

    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = topPadding)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    AsyncImage(
                        model = user.pictureUri,
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

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "ID: ${user.id}",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "E-mail: ${user.email}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(40.dp))

                ProfileScreenStringDataSection(
                    label = Res.string.nickname,
                    data = user.name,
                    lastEditTimestamp = user.nameLastUpdated,
                    editable = true, // todo(): boolean logic datetime
                    onEdit = { updateNameVisible = true },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(48.dp))

                ProfileScreenStringDataSection(
                    label = Res.string.victory_message,
                    data = user.victoryMessage,
                    lastEditTimestamp = user.victoryMessageLastUpdated,
                    editable = true, // todo(): boolean logic datetime
                    onEdit = { updateMessageVisible = true },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(48.dp))

                ProfileScreenListDataSection(
                    event = { event(it) }
                )
            }

            SingleButtonRow(
                onClick = { event(ProfileScreenEvent.PopBack) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
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
                    event(ProfileScreenEvent.UpdateName(it))
                }
            },
            currentValue = user.name,
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
                    event(ProfileScreenEvent.UpdateMessage(it))
                }
            },
            currentValue = user.victoryMessage,
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
            sheetState = updateMessageState
        )
    }

    if (updatePictureVisible) {
        UpdatePictureBottomSheet(
            sheetState = updatePictureState,
            currentName = user.name,
            currentPicture = user.pictureUri,
            availablePictures = profilePictures,
            onUpdatePicture = { event(ProfileScreenEvent.UpdatePicture(it)) },
            onHide = {
                coroutineScope.launch {
                    updatePictureState.hide()
                    updatePictureVisible = false
                }
            }
        )
    }

    if (signOutVisible) {
        GenericActionDialog(
            onDismiss = { signOutVisible = false },
            onConfirm = {
                signOutVisible = false
                event(ProfileScreenEvent.SignOut)
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body
        )
    }

    if (deleteAccountVisible) {
        GenericActionDialog(
            onDismiss = { deleteAccountVisible = false },
            onConfirm = {
                deleteAccountVisible = false
                event(ProfileScreenEvent.DeleteAccount)
            },
            title = Res.string.delete_account_title,
            body = Res.string.delete_account_body
        )
    }
}
