package ui.presentation.join_room.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LazyColumnStickyHeader(
    header: StringResource,
    modifier: Modifier = Modifier,
) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = stringResource(header),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
            )
        }
}