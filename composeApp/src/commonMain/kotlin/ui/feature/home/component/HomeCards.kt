package ui.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_blue_gradient
import themedbingo.composeapp.generated.resources.bg_green_gradient
import themedbingo.composeapp.generated.resources.bg_orange_gradient
import themedbingo.composeapp.generated.resources.classic_bingo_type
import themedbingo.composeapp.generated.resources.home_classic_bingo_desc
import themedbingo.composeapp.generated.resources.home_themed_bingo_desc
import themedbingo.composeapp.generated.resources.img_bingo_balls
import themedbingo.composeapp.generated.resources.img_mascot_bossy_smiling
import themedbingo.composeapp.generated.resources.img_smiling_squirrel
import themedbingo.composeapp.generated.resources.themed_bingo_type
import themedbingo.composeapp.generated.resources.vip_description
import themedbingo.composeapp.generated.resources.vip_full_access
import ui.feature.core.text.OutlinedText
import ui.theme.AppTheme
import ui.theme.classicBingoOnColor
import ui.theme.classicBingoPrimaryColor
import ui.theme.homeOnColor
import ui.theme.themedBingoOnColor
import ui.theme.themedBingoPrimaryColor
import ui.theme.vipOnColor
import ui.theme.vipPrimaryColor

@Composable
fun ThemedBingoCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(maxWidth * 0.88f)
                .height(100.dp)
                .shadow(3.dp, RoundedCornerShape(12.dp))
                .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(Res.drawable.bg_blue_gradient),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer { scaleY = -1f }
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.matchParentSize()
            ) {
                OutlinedText(
                    text = stringResource(Res.string.themed_bingo_type),
                    fontSize = 24,
                    strokeWidth = 2f,
                    fontColor = themedBingoOnColor,
                    strokeColor = themedBingoPrimaryColor,
                    modifier = Modifier.padding(start = 16.dp, end = 44.dp)
                )

                Text(
                    text = stringResource(Res.string.home_themed_bingo_desc),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 14.sp,
                    color = homeOnColor,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 60.dp, top = 4.dp)
                )
            }
        }

        Image(
            painter = painterResource(Res.drawable.img_smiling_squirrel),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer { scaleX = -1f }
        )
    }
}

@Composable
fun ClassicBingoCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(maxWidth * 0.88f)
                .height(100.dp)
                .shadow(3.dp, RoundedCornerShape(12.dp))
                .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(Res.drawable.bg_green_gradient),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer { scaleY = -1f }
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.matchParentSize()
            ) {
                OutlinedText(
                    text = stringResource(Res.string.classic_bingo_type),
                    textAlign = TextAlign.End,
                    fontSize = 24,
                    strokeWidth = 2f,
                    fontColor = classicBingoOnColor,
                    strokeColor = classicBingoPrimaryColor,
                    modifier = Modifier.padding(end = 16.dp, start = 60.dp)
                )

                Text(
                    text = stringResource(Res.string.home_classic_bingo_desc),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 14.sp,
                    color = homeOnColor,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 16.dp, start = 44.dp, top = 4.dp)
                )
            }
        }

        Image(
            painter = painterResource(Res.drawable.img_bingo_balls),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(100.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun VipCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(maxWidth * 0.88f)
                .height(100.dp)
                .shadow(3.dp, RoundedCornerShape(12.dp))
                .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(Res.drawable.bg_orange_gradient),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.matchParentSize()
            ) {
                OutlinedText(
                    text = stringResource(Res.string.vip_full_access),
                    fontSize = 24,
                    strokeWidth = 2f,
                    fontColor = vipOnColor,
                    strokeColor = vipPrimaryColor,
                    modifier = Modifier.padding(start = 16.dp, end = 44.dp)
                )

                Text(
                    text = stringResource(Res.string.vip_description),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 14.sp,
                    color = vipOnColor,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 60.dp, top = 4.dp)
                )
            }
        }

        Image(
            painter = painterResource(Res.drawable.img_mascot_bossy_smiling),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ThemedBingoCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            ClassicBingoCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            VipCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
