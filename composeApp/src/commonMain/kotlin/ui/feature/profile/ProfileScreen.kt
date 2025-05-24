@file:OptIn(ExperimentalMaterial3Api::class)

package ui.feature.profile

import OperationalSystem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import domain.feature.user.model.Tier
import domain.feature.user.model.User
import getPlatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_waterfall
import themedbingo.composeapp.generated.resources.copied_to_clipboard
import themedbingo.composeapp.generated.resources.delete_account_body
import themedbingo.composeapp.generated.resources.delete_account_title
import themedbingo.composeapp.generated.resources.ic_copy
import themedbingo.composeapp.generated.resources.ic_edit
import themedbingo.composeapp.generated.resources.ic_exit
import themedbingo.composeapp.generated.resources.ic_trash
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.profile_delete_account
import themedbingo.composeapp.generated.resources.profile_email
import themedbingo.composeapp.generated.resources.profile_my_account
import themedbingo.composeapp.generated.resources.profile_my_data
import themedbingo.composeapp.generated.resources.profile_nickname
import themedbingo.composeapp.generated.resources.profile_screen
import themedbingo.composeapp.generated.resources.profile_sign_out
import themedbingo.composeapp.generated.resources.profile_user_id
import themedbingo.composeapp.generated.resources.profile_victory_message
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.victory_message
import ui.feature.core.CustomTopBar
import ui.feature.core.ErrorScreen
import ui.feature.core.LoadingScreen
import ui.feature.core.bottomSheet.UpdateBottomSheet
import ui.feature.core.dialog.GenericActionDialog
import ui.feature.core.text.OutlinedShadowedText
import ui.feature.profile.component.ProfileActionCard
import ui.feature.profile.component.picture.ChangePictureBottomSheet
import ui.feature.profile.component.picture.ProfileScreenUserPicture
import ui.theme.AppTheme
import ui.theme.error
import ui.theme.homeOnColor
import ui.theme.homeSecondaryColor
import ui.theme.onError
import ui.util.collectInLaunchedEffect
import util.getLocalDateTimeNow

@Composable
fun ProfileScreen(component: ProfileScreenComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    component.messages.collectInLaunchedEffect { message ->
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = true
        )
    }

    when (uiState) {
        ProfileScreenUiState.Loading -> {
            LoadingScreen()
        }

        is ProfileScreenUiState.Error -> {
            ErrorScreen(
                onRetry = { component.uiEvent(ProfileScreenUiEvent.Retry) },
                onPopBack = { component.uiEvent(ProfileScreenUiEvent.PopBack) },
                errorMessage = (uiState as ProfileScreenUiState.Error).message
            )
        }

        is ProfileScreenUiState.Success -> {
            ProfileScreen(
                uiState = uiState as ProfileScreenUiState.Success,
                snackbarHostState = snackbarHostState,
                onUiEvent = component::uiEvent,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ProfileScreen(
    uiState: ProfileScreenUiState.Success,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (ProfileScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue -> newValue != SheetValue.Hidden }
    )

    var showPicturesBottomSheet by remember { mutableStateOf(false) }
    var showUserNameBottomSheet by remember { mutableStateOf(false) }
    var showVictoryMessageBottomSheet by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CustomTopBar(
                text = stringResource(Res.string.profile_screen),
                primaryColor = homeSecondaryColor,
                onPrimaryColor = homeOnColor,
                onBackPressed = { onUiEvent(ProfileScreenUiEvent.PopBack) }
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(Res.drawable.bg_waterfall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
            ) {
                ProfileScreenUserPicture(
                    pictureUri = uiState.user.pictureUri,
                    onPictureChange = { showPicturesBottomSheet = true }
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 16.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(homeOnColor)
                )

                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedShadowedText(
                        text = stringResource(Res.string.profile_my_data),
                        fontSize = 24,
                        strokeWidth = 2f,
                        fontColor = homeOnColor,
                        strokeColor = homeSecondaryColor,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp)
                    )

                    ProfileActionCard(
                        label = stringResource(Res.string.profile_nickname),
                        value = uiState.user.name,
                        icon = painterResource(Res.drawable.ic_edit),
                        onClick = { showUserNameBottomSheet = true },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )

                    ProfileActionCard(
                        label = stringResource(Res.string.profile_victory_message),
                        value = uiState.user.victoryMessage,
                        icon = painterResource(Res.drawable.ic_edit),
                        onClick = { showVictoryMessageBottomSheet = true },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )

                    ProfileActionCard(
                        label = stringResource(Res.string.profile_user_id),
                        value = uiState.user.id,
                        icon = painterResource(Res.drawable.ic_copy),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(uiState.user.id))
                            coroutineScope.launch {
                                if (getPlatform().system == OperationalSystem.IOS) {
                                    snackbarHostState.showSnackbar(
                                        message = getString(Res.string.copied_to_clipboard),
                                        withDismissAction = true
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )

                    ProfileActionCard(
                        label = stringResource(Res.string.profile_email),
                        value = uiState.user.email,
                        icon = painterResource(Res.drawable.ic_copy),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(uiState.user.email))
                            coroutineScope.launch {
                                if (getPlatform().system == OperationalSystem.IOS) {
                                    snackbarHostState.showSnackbar(
                                        message = getString(Res.string.copied_to_clipboard),
                                        withDismissAction = true
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )

                    OutlinedShadowedText(
                        text = stringResource(Res.string.profile_my_account),
                        fontSize = 24,
                        strokeWidth = 2f,
                        fontColor = homeOnColor,
                        strokeColor = homeSecondaryColor,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 20.dp, top = 32.dp)
                    )

                    ProfileActionCard(
                        value = stringResource(Res.string.profile_sign_out),
                        icon = painterResource(Res.drawable.ic_exit),
                        onClick = { showSignOutDialog = true },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )

                    ProfileActionCard(
                        value = stringResource(Res.string.profile_delete_account),
                        icon = painterResource(Res.drawable.ic_trash),
                        onClick = { showDeleteAccountDialog = true },
                        colors = CardDefaults.cardColors(
                            containerColor = error,
                            contentColor = onError
                        ),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

    if (showPicturesBottomSheet) {
        ChangePictureBottomSheet(
            currentPicture = uiState.user.pictureUri,
            sheetState = bottomSheetState,
            onCancel = {
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showPicturesBottomSheet = false }
            },
            onPictureChange = { newPic ->
                onUiEvent(ProfileScreenUiEvent.UpdatePicture(newPic))
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showPicturesBottomSheet = false }
            }
        )
    }

    if (showUserNameBottomSheet) {
        UpdateBottomSheet(
            onDismiss = {
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showUserNameBottomSheet = false }
            },
            onConfirm = { newNickname ->
                onUiEvent(ProfileScreenUiEvent.UpdateName(newNickname))
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showUserNameBottomSheet = false }
            },
            currentValue = uiState.user.name,
            title = Res.string.update_nickname_title,
            body = Res.string.update_nickname_body,
            label = Res.string.nickname,
            maxLength = 20
        )
    }

    if (showVictoryMessageBottomSheet) {
        UpdateBottomSheet(
            onDismiss = {
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showVictoryMessageBottomSheet = false }
            },
            onConfirm = { newMessage ->
                onUiEvent(ProfileScreenUiEvent.UpdateMessage(newMessage))
                coroutineScope.launch { bottomSheetState.hide() }
                    .invokeOnCompletion { showVictoryMessageBottomSheet = false }
            },
            currentValue = uiState.user.victoryMessage,
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
            maxLength = 80
        )
    }

    if (showSignOutDialog) {
        GenericActionDialog(
            onDismiss = { showSignOutDialog = false },
            onConfirm = {
                showSignOutDialog = false
                onUiEvent(ProfileScreenUiEvent.SignOut)
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body
        )
    }

    if (showDeleteAccountDialog) {
        GenericActionDialog(
            onDismiss = { showDeleteAccountDialog = false },
            onConfirm = {
                showDeleteAccountDialog = false
                onUiEvent(ProfileScreenUiEvent.DeleteAccount)
            },
            title = Res.string.delete_account_title,
            body = Res.string.delete_account_body,
            permanentAction = true
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        ProfileScreen(
            uiState = ProfileScreenUiState.Success(
                user = User(
                    id = "38469DJ8F10CN1CHSDAL",
                    createdAt = getLocalDateTimeNow(),
                    updatedAt = null,
                    email = "alton.nolan@example.com",
                    name = "Alton Nolan",
                    victoryMessage = "Para o Alton e avante",
                    pictureUri = "",
                    tier = Tier.FREE
                )
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onUiEvent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
