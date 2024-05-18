package ui.presentation.host

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.presentation.util.WindowInfo

@Composable
fun HostScreen(
    component: HostScreenComponent,
    windowInfo: WindowInfo
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Host Screen")
        Button(
            onClick = { component.popBack() }
        ) {
            Text("Back")
        }
    }

}