package ui.presentation.core.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_edit
import ui.theme.LuckiestGuyFontFamily
import ui.theme.PoppinsFontFamily

@Composable
fun SingleInfoEditCard(
    label: String = "",
    currentValue: String = "",
    onClick: () -> Unit = {},
    containerColor: Color = Color.White,
    contentColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, contentColor),
        colors = CardDefaults.cardColors().copy(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontFamily = PoppinsFontFamily()
                )

                Text(
                    text = currentValue,
                    fontSize = 20.sp,
                    fontFamily = LuckiestGuyFontFamily(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Icon(
                painter = painterResource(Res.drawable.ic_edit),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        SingleInfoEditCard(
            label = "Nickname",
            currentValue = "The Notorious B.I.G.",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
