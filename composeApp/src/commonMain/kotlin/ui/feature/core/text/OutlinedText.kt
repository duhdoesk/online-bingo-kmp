package ui.feature.core.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.LuckiestGuyFontFamily

@Composable
fun OutlinedText(
    text: String,
    fontSize: Int,
    strokeWidth: Float,
    fontColor: Color,
    strokeColor: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = 1
) {
    Box(
        modifier = modifier.offset(y = (4).dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = fontColor,
                fontSize = fontSize.sp,
                fontFamily = LuckiestGuyFontFamily(),
                textAlign = textAlign
            ),
            maxLines = maxLines
        )

        Text(
            text = text,
            style = TextStyle(
                color = strokeColor,
                drawStyle = Stroke(strokeWidth),
                fontSize = fontSize.sp,
                fontFamily = LuckiestGuyFontFamily(),
                textAlign = textAlign
            ),
            maxLines = maxLines
        )
    }
}
