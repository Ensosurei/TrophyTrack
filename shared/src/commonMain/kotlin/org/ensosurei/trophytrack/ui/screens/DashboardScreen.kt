package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.ensosurei.trophytrack.ui.theme.purple
import org.ensosurei.trophytrack.ui.theme.white
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_home
import trophytrack.shared.generated.resources.ic_inbox
import trophytrack.shared.generated.resources.ic_library
import trophytrack.shared.generated.resources.ic_plus
import trophytrack.shared.generated.resources.ic_profile

@Composable
fun DashboardScreen(){
    var testButton by remember { mutableStateOf(false) }
    var status by remember { mutableIntStateOf(0) }
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { testButton = true },
                containerColor = purple,
                contentColor = white
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_plus),
                    contentDescription = "Añadir"
                )
            }
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = (status == 0),
                    onClick = { status = 0 },
                    icon = { Icon(
                        vectorResource(Res.drawable.ic_home),
                        contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = (status == 1),
                    onClick = { status = 1},
                    icon = { Icon(
                        vectorResource(Res.drawable.ic_library),
                        contentDescription = "Library") },
                    label = { Text("Library") }
                )
                NavigationBarItem(
                    selected = false,
                    enabled = false,
                    onClick = { },
                    icon = {  },
                    label = { }
                )
                NavigationBarItem(
                    selected = (status == 2),
                    onClick = { status = 2},
                    icon = { Icon(
                        vectorResource(Res.drawable.ic_inbox),
                        contentDescription = "Activity") },
                    label = { Text("Activity") }
                )
                NavigationBarItem(
                    selected = (status == 3),
                    onClick = { status = 3},
                    icon = { Icon(
                        vectorResource(Res.drawable.ic_profile),
                        contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ){ paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues))
    }
}