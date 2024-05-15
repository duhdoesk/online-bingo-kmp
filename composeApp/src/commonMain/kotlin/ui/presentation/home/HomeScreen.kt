package ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.navigation.Configuration

@Composable
fun HomeScreen(component: HomeScreenComponent) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Home Screen")

        Button(
            onClick = { component.navigate(Configuration.CreateScreen) }
        ) {
            Text("Create")
        }

        Button(
            onClick = { component.navigate(Configuration.JoinScreen) }
        ) {
            Text("Join")
        }

        Button(
            onClick = { component.navigate(Configuration.ThemesScreen) }
        ) {
            Text("Themes")
        }

        Button(
            onClick = { component.navigate(Configuration.HostScreen) }
        ) {
            Text("Host")
        }

        Button(
            onClick = { component.navigate(Configuration.PlayScreen) }
        ) {
            Text("Play")
        }
    }
}