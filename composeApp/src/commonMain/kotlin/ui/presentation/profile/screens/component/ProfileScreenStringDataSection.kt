package ui.presentation.profile.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.toMilliseconds
import domain.util.datetime.formatDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.can_change_in
import themedbingo.composeapp.generated.resources.edit_button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreenStringDataSection(
    label: StringResource,
    data: String,
    lastEditTimestamp: Timestamp,
    editable: Boolean,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val futureInstant = lastEditTimestamp.toMilliseconds().toLong() + 259200000 // value that corresponds to 3 days
    val futureInstantMs = Instant.fromEpochMilliseconds(futureInstant)
    val dateTime = futureInstantMs.toLocalDateTime(TimeZone.currentSystemDefault())

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(label),
                modifier = Modifier
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = data,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = { onEdit() },
            enabled = editable,
            modifier = Modifier
                .padding(end = 16.dp),
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

    HorizontalDivider(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .fillMaxWidth()
    )

    if (!editable) {
        Text(
            text = "${stringResource(Res.string.can_change_in)} ${dateTime.format(formatDateTime())}",
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
