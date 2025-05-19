package ui.feature.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_copy
import themedbingo.composeapp.generated.resources.ic_edit
import themedbingo.composeapp.generated.resources.ic_trash
import ui.theme.AppTheme
import ui.theme.LilitaOneFontFamily
import ui.theme.error
import ui.theme.homeOnColor
import ui.theme.homeSecondaryColor
import ui.theme.onError

@Composable
fun ProfileActionCard(
    colors: CardColors = CardDefaults.cardColors().copy(
        containerColor = homeOnColor.copy(alpha = 0.9f),
        contentColor = homeSecondaryColor
    ),
    icon: Painter,
    label: String? = null,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(2.dp, colors.contentColor),
        colors = colors,
        elevation = CardDefaults.cardElevation(3.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                label?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = value,
                    fontFamily = LilitaOneFontFamily(),
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Icon(
                painter = icon,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ProfileActionCard(
                    label = "apelido",
                    value = "duhdoesk",
                    icon = painterResource(Res.drawable.ic_edit),
                    onClick = {}
                )

                ProfileActionCard(
                    label = "Grito de vitória",
                    value = "O Bingo temático é demais!",
                    icon = painterResource(Res.drawable.ic_edit),
                    onClick = {}
                )

                ProfileActionCard(
                    label = "ID do usuário",
                    value = "jd82y382disdhnd1cn",
                    icon = painterResource(Res.drawable.ic_copy),
                    onClick = {}
                )

                ProfileActionCard(
                    colors = CardDefaults.cardColors().copy(
                        containerColor = error,
                        contentColor = onError
                    ),
                    value = "APAGAR CONTA",
                    icon = painterResource(Res.drawable.ic_trash),
                    onClick = {}
                )
            }
        }
    }
}
