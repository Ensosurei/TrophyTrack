package org.ensosurei.trophytrack.ui.screens

import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.ensosurei.trophytrack.ui.theme.purple
import org.ensosurei.trophytrack.ui.theme.white
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun DashboardScreen(){
    var testButton by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { testButton = true },
                containerColor = purple,
                contentColor = white
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir"
                )
            }
        }
    ){

    }
}