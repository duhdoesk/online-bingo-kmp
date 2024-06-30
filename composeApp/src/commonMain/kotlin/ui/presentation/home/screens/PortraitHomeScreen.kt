package ui.presentation.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.join_room
import themedbingo.composeapp.generated.resources.new_room
import themedbingo.composeapp.generated.resources.profile
import ui.navigation.Configuration
import ui.presentation.home.screens.components.HomeScreenHeader
import ui.presentation.home.event.HomeScreenEvent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitHomeScreen(
    event: (event: HomeScreenEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val buttonModifier = Modifier
            .width(240.dp)

        HomeScreenHeader()

        Spacer(Modifier.height(64.dp))

        Button(
            onClick = {
                event(HomeScreenEvent.Navigate(Configuration.CreateScreen))
            },
            modifier = buttonModifier
        ) {
            Icon(
                Icons.Filled.Create,
                contentDescription = stringResource(resource = Res.string.new_room)
            )
            Spacer(Modifier.width(8.dp))
            Text(stringResource(resource = Res.string.new_room))
        }

        Button(
            onClick = {
                event(HomeScreenEvent.Navigate(Configuration.JoinScreen))
            },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(resource = Res.string.join_room)
            )
            Spacer(Modifier.width(8.dp))
            Text(stringResource(resource = Res.string.join_room))
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .width(120.dp)
        )

        OutlinedButton(
            onClick = {
                event(HomeScreenEvent.Navigate(Configuration.ProfileScreen))
            },
            modifier = buttonModifier
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = stringResource(resource = Res.string.profile)
            )
            Spacer(Modifier.width(8.dp))
            Text(stringResource(resource = Res.string.profile))
        }
    }
}