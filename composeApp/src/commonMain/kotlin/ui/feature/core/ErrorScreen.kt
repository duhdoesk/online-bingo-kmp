package ui.feature.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.back_button
import themedbingo.composeapp.generated.resources.error
import themedbingo.composeapp.generated.resources.ic_back
import themedbingo.composeapp.generated.resources.oops
import themedbingo.composeapp.generated.resources.retry_button
import themedbingo.composeapp.generated.resources.sad_tiger

@Composable
fun ErrorScreen(
    retry: () -> Unit,
    popBack: () -> Unit,
    message: StringResource
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.sad_tiger),
                contentDescription = stringResource(Res.string.error),
                modifier = Modifier.size(160.dp)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = stringResource(Res.string.oops),
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(message),
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { retry() },
                modifier = Modifier.width(200.dp)
            ) { Text(stringResource(Res.string.retry_button)) }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { popBack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = stringResource(Res.string.back_button)
                )

                Text(
                    text = stringResource(Res.string.back_button),
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(
    onPopBack: () -> Unit,
    onRetry: () -> Unit,
    errorMessage: String
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.sad_tiger),
                contentDescription = stringResource(Res.string.error),
                modifier = Modifier.size(160.dp)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = stringResource(Res.string.oops),
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onRetry() },
                modifier = Modifier.width(200.dp)
            ) { Text(stringResource(Res.string.retry_button)) }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { onPopBack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = stringResource(Res.string.back_button)
                )

                Text(
                    text = stringResource(Res.string.back_button),
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}
