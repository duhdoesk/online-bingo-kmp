package ui.feature.createRoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_pool_water
import themedbingo.composeapp.generated.resources.classic_bingo_type
import themedbingo.composeapp.generated.resources.themed_bingo_type
import ui.feature.core.CustomTopBar
import ui.theme.lobbyOnColor
import ui.theme.lobbySecondaryColor
import ui.util.WindowInfo
import ui.util.collectInLaunchedEffect

@Composable
fun CreateRoomScreen(
    component: CreateRoomScreenComponent,
    windowInfo: WindowInfo
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    component.uiMessage.collectInLaunchedEffect { message ->
        snackbarHostState.showSnackbar(message)
    }

    CreateRoomScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onUiEvent = component::onUiEvent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun CreateRoomScreenContent(
    uiState: CreateRoomUiState,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (CreateRoomUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            val title = when (uiState.type) {
                CreateRoomUiState.Type.Classic -> Res.string.classic_bingo_type
                is CreateRoomUiState.Type.Themed -> Res.string.themed_bingo_type
            }

            CustomTopBar(
                text = stringResource(title),
                primaryColor = lobbySecondaryColor,
                onPrimaryColor = lobbyOnColor,
                onBackPressed = { onUiEvent(CreateRoomUiEvent.OnPopBack) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier
    ) { innerPadding ->
        Box {
            Image(
                painter = painterResource(Res.drawable.bg_pool_water),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .widthIn(max = 600.dp)
        ) {
        }
    }
}
