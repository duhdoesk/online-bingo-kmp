@file:OptIn(ExperimentalMaterial3Api::class)

package ui.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import domain.feature.user.model.Tier
import domain.feature.user.model.User
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_waterfall
import themedbingo.composeapp.generated.resources.profile_screen
import ui.feature.core.CustomTopBar
import ui.feature.core.ErrorScreen
import ui.feature.core.LoadingScreen
import ui.feature.profile.component.picture.ChangePictureBottomSheet
import ui.feature.profile.component.picture.ProfileScreenUserPicture
import ui.theme.AppTheme
import ui.theme.homeOnColor
import ui.theme.homeSecondaryColor
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
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue -> newValue != SheetValue.Hidden }
    )
    var showPicturesBottomSheet by remember { mutableStateOf(false) }

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
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileScreenUserPicture(
                    pictureUri = uiState.user.pictureUri,
                    onPictureChange = { showPicturesBottomSheet = true }
                )
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
