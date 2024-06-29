package ui.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.presentation.util.WindowInfo

@Composable
fun ProfileScreen(component: ProfileScreenComponent, windowInfo: WindowInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { component.signOut() }) {
            Text("Sign Out")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { component.popBack() }) {
            Text("Back")
        }
    }
}