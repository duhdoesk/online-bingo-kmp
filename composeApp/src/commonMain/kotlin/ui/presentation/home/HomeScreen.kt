package ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.navigation.Configuration
import ui.presentation.util.rememberWindowInfo

@Composable
fun HomeScreen(component: HomeScreenComponent) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        propagateMinConstraints = true
    ) {

        val boxWidth = this.maxWidth
        val boxHeight = this.maxHeight

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text("Current Screen")
            Text("Home Screen")

            val windowInfo = rememberWindowInfo(screenHeight = boxHeight, screenWidth = boxWidth)

            Spacer(Modifier.height(16.dp))
            Text("Screen Orientation")
            Text(windowInfo.screenOrientation.toString())

            Spacer(Modifier.height(16.dp))
            Text("Height Info")
            Text(windowInfo.screenHeight.toString())
            Text(windowInfo.screenHeightInfo.toString())

            Spacer(Modifier.height(16.dp))
            Text("Width Info")
            Text(windowInfo.screenWidth.toString())
            Text(windowInfo.screenWidthInfo.toString())

//            Button(
//                onClick = { component.navigate(Configuration.CreateScreen) }
//            ) {
//                Text("Create")
//            }
//
//            Button(
//                onClick = { component.navigate(Configuration.JoinScreen) }
//            ) {
//                Text("Join")
//            }
//
//            Button(
//                onClick = { component.navigate(Configuration.ThemesScreen) }
//            ) {
//                Text("Themes")
//            }
//
//            Button(
//                onClick = { component.navigate(Configuration.HostScreen) }
//            ) {
//                Text("Host")
//            }
//
//            Button(
//                onClick = { component.navigate(Configuration.PlayScreen) }
//            ) {
//                Text("Play")
//            }
        }
    }

}