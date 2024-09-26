package ui.presentation.profile.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.account
import themedbingo.composeapp.generated.resources.delete_account

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreenListDataSection(
    modifier: Modifier = Modifier,
    onUpdatePassword: () -> Unit,
    onDeleteAccount: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Text(
            text = stringResource(Res.string.account),
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDeleteAccount() },
        ) {
            Text(
                text = stringResource(Res.string.delete_account),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            )
        }

        ProfileScreenCustomDivider()
    }
}

@Composable
fun ProfileScreenCustomDivider() {
    HorizontalDivider(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
}