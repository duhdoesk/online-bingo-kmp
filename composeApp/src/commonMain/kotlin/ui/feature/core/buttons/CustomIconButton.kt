package ui.feature.core.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_back
import ui.theme.AppTheme
import ui.theme.homeOnColor

@Composable
fun CustomIconButton(
    icon: Painter = painterResource(Res.drawable.ic_back),
    colors: ButtonColors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    buttonSize: Dp = 48.dp,
    enabled: Boolean = true,
    elevation: Dp = 3.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(buttonSize),
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(4.dp, colors.contentColor),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(elevation)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = colors.contentColor
        )
    }
}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    colors: ButtonColors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    buttonSize: Dp = 48.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(buttonSize),
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(4.dp, colors.contentColor),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.contentColor
        )
    }
}

@Composable
fun CustomIconButton(
    icon: Painter,
    background: Painter,
    contentColor: Color,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    buttonSize: Dp = 48.dp
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .border(
                width = 4.dp,
                color = homeOnColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Icon(
            painter = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        CustomIconButton(
            modifier = Modifier
                .padding(20.dp)
                .size(56.dp)
        )
    }
}
