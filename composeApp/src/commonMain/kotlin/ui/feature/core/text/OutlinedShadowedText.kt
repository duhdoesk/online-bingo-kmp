package ui.feature.core.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.LuckiestGuyFontFamily

@Composable
fun OutlinedShadowedText(
    text: String,
    fontSize: Int,
    strokeWidth: Float,
    fontColor: Color,
    strokeColor: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE
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
                textAlign = textAlign,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.60f),
                    offset = Offset(x = 0f, y = 4f),
                    blurRadius = 4f
                )
            ),
            maxLines = maxLines,
            overflow = overflow
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
            maxLines = maxLines,
            overflow = overflow
        )
    }
}
