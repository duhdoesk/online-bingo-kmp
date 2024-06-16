package ui.presentation.join_room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.forEach
import ui.presentation.util.WindowInfo

@Composable
fun JoinScreen(
    component: JoinScreenComponent,
    windowInfo: WindowInfo
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val roomsList = component
            .roomsList
            .collectAsState()
            .value

        Text("Join Screen")

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            roomsList.forEach { room ->
                item {
                    Card(
                        onClick = { component.joinRoom(room.id) },
                    ) {
                        Text(
                            text = room.name,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        Button(
            onClick = { component.popBack() }
        ) {
            Text("Back")
        }
    }

}