package ui.presentation.create_user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.create_user.event.CreateUserEvent
import ui.presentation.create_user.screens.CreateUserScreenComposition
import ui.presentation.util.dialog.GenericErrorDialog

@ExperimentalMaterial3Api
@ExperimentalResourceApi
@Composable
fun CreateUserScreen(viewModel: CreateUserViewModel) {

    /**
     * Screen State listener
     */
    val screenState by viewModel.screenState.collectAsState()

    /**
     * Informs the viewModel that the UI is ready
     */
    LaunchedEffect(Unit) { viewModel.uiEvent(CreateUserEvent.UiLoaded) }

    /**
     * Screen composing
     */
    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize(),
        ) {
            CreateUserScreenComposition(
                screenState = screenState,
                event = { viewModel.uiEvent(it) },
            )

            /**
             * Blurry screen and show progress indicator when processing
             */
            if (screenState.processing) {
                Box {
                    Surface(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        modifier = Modifier.fillMaxSize().blur(40.dp),
                    ) {}
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp).align(Alignment.Center)
                    )
                }
            }

            /**
             * Show error if there is any
             */
            if (viewModel.errorDialogState.isVisible.value) {
                GenericErrorDialog(
                    onDismiss = { viewModel.errorDialogState.hideDialog() },
                    body = viewModel.errorDialogState.dialogData as String?,
                )
            }
        }
    }
}