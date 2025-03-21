package ui.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryActionButton(
    enabled: Boolean = false,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(32.dp)
        ) {
            Text(
                text = text.uppercase(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
