package ui.presentation.sign_in.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.apple_button_logo
import themedbingo.composeapp.generated.resources.blue_ball
import themedbingo.composeapp.generated.resources.google_button_logo
import themedbingo.composeapp.generated.resources.sign_in_apple
import themedbingo.composeapp.generated.resources.sign_in_google
import ui.presentation.common.components.HomeScreenHeader
import ui.presentation.sign_in.event.SignInScreenEvent
import ui.presentation.sign_in.state.SignInScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UniqueSignInScreen(
    uiState: SignInScreenUIState,
    event: (event: SignInScreenEvent) -> Unit,
    onStartGoogleAuth: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeScreenHeader()

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = { onStartGoogleAuth() },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .widthIn(min = 240.dp, max = 360.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.White,
                contentColor = Color.Gray,
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.google_button_logo),
                    contentDescription = stringResource(Res.string.sign_in_google),
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(12.dp))
                Text(stringResource(Res.string.sign_in_google))
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { onStartGoogleAuth() },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .widthIn(min = 240.dp, max = 360.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black,
                contentColor = Color.White,
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.apple_button_logo),
                    contentDescription = stringResource(Res.string.sign_in_apple),
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(12.dp))
                Text(stringResource(Res.string.sign_in_apple))
            }
        }
    }
}