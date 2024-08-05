package ui.presentation.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.user.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button
import themedbingo.composeapp.generated.resources.edit_button
import themedbingo.composeapp.generated.resources.exit_button
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.user_avatar
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.common.BottomButtonRow
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.component.ProfileScreenListDataSection
import ui.presentation.profile.screens.component.ProfileScreenStringDataSection
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitProfileScreen(
    windowInfo: WindowInfo,
    uiState: ProfileScreenUIState.Success,
    event: (event: ProfileScreenEvent) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .imePadding()
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
                        model = uiState.user.pictureUri,
                        contentDescription = stringResource(Res.string.user_avatar),
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        clipToBounds = true,
                    )

                    IconButton(
                        onClick = { event(ProfileScreenEvent.UpdatePicture) },
                        enabled = true,
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(Res.string.edit_button)
                            )
                        }
                    )
                }

                Spacer(Modifier.height(48.dp))

                ProfileScreenStringDataSection(
                    label = Res.string.nickname,
                    data = uiState.user.name,
                    lastEditTimestamp = uiState.user.nameLastUpdated,
                    editable = true, //todo(): boolean logic datetime
                    onEdit = { event(ProfileScreenEvent.UpdateName) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(48.dp))

                ProfileScreenStringDataSection(
                    label = Res.string.victory_message,
                    data = uiState.user.victoryMessage,
                    lastEditTimestamp = uiState.user.victoryMessageLastUpdated,
                    editable = true, //todo(): boolean logic datetime
                    onEdit = { event(ProfileScreenEvent.UpdateVictoryMessage) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(48.dp))

                ProfileScreenListDataSection(
                    event = { event(it) }
                )
            }

            BottomButtonRow(
                leftEnabled = true,
                rightEnabled = true,
                leftClicked = { event(ProfileScreenEvent.PopBack) },
                rightClicked = { event(ProfileScreenEvent.SignOut) },
                leftText = Res.string.back_button,
                rightText = Res.string.exit_button,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
            )
        }
    }
}
