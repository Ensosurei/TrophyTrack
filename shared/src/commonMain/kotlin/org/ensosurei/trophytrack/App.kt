package org.ensosurei.trophytrack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.ensosurei.trophytrack.ui.components.GamesGrid
import org.jetbrains.compose.resources.painterResource

import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.compose_multiplatform

@Composable
fun App(container: AppContainer) {
    val userPrefs = container.userPreferences
    val localName by userPrefs.localNameFlow.collectAsState(initial = null)
    val localAvatar by userPrefs.localAvatarPathFlow.collectAsState(initial = null)
    val steamName by userPrefs.steamNameFlow.collectAsState(initial = null)
    val steamId by userPrefs.steamIdFlow.collectAsState(initial = null)
    val steamAvatar by userPrefs.steamAvatarFlow.collectAsState(initial = null)
    val games by container.db.gameDao().getAllGames().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GamesGrid(gameList = games)
            Text(text = "$localName, $localAvatar")
            Button(onClick = {
                scope.launch {
                    userPrefs.updateLocalProfile("Mercedes-Tester", "avatar_prueba.png")
                }
            }) {
                Text("Guardar Nombre")
            }
            Button(onClick = {
                scope.launch {
                    userPrefs.clearLocalProfile()
                }
            }) {
                Text("Borrar Todo")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

