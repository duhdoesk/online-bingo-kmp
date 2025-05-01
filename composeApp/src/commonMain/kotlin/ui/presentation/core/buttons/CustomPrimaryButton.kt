package ui.presentation.core.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AppTheme
import ui.theme.LuckiestGuyFontFamily

@Composable
fun CustomPrimaryButton(
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 60.dp,
    enabled: Boolean = true,
    elevation: Dp = 3.dp
) {
    Button(
        colors = colors,
        onClick = onClick,
        modifier = modifier.height(height),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(4.dp, colors.contentColor),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(elevation)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontFamily = LuckiestGuyFontFamily(),
            modifier = Modifier.offset(y = 4.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        CustomPrimaryButton(
            text = "Confirmar",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
