package org.ensosurei.trophytrack.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.ensosurei.trophytrack.ui.theme.purple
import org.ensosurei.trophytrack.ui.theme.white
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_home
import trophytrack.shared.generated.resources.ic_inbox
import trophytrack.shared.generated.resources.ic_library
import trophytrack.shared.generated.resources.ic_plus
import trophytrack.shared.generated.resources.ic_profile

@Composable
fun BarNavigation(
    currentStatus: Int,
    onStatusChange: (Int) -> Unit,
    onFabClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
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
                    selected = (currentStatus == 0),
                    onClick = { onStatusChange(0) },
                    icon = {
                        Icon(
                            vectorResource(Res.drawable.ic_home),
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = (currentStatus == 1),
                    onClick = { onStatusChange(1) },
                    icon = {
                        Icon(
                            vectorResource(Res.drawable.ic_library),
                            contentDescription = "Library"
                        )
                    },
                    label = { Text("Library") }
                )
                NavigationBarItem(
                    selected = false,
                    enabled = false,
                    onClick = { },
                    icon = { },
                    label = { }
                )
                NavigationBarItem(
                    selected = (currentStatus == 2),
                    onClick = { onStatusChange(2) },
                    icon = {
                        Icon(
                            vectorResource(Res.drawable.ic_inbox),
                            contentDescription = "Activity"
                        )
                    },
                    label = { Text("Activity") }
                )
                NavigationBarItem(
                    selected = (currentStatus == 3),
                    onClick = { onStatusChange(3) },
                    icon = {
                        Icon(
                            vectorResource(Res.drawable.ic_profile),
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") }
                )
            }
        }
    ){ paddingValues ->
        content(paddingValues)
    }
}