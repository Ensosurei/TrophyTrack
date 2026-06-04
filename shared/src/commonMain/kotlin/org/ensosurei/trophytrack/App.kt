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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.ensosurei.trophytrack.ui.components.GamesGrid
import org.ensosurei.trophytrack.ui.screens.DashboardScreen
import org.ensosurei.trophytrack.ui.theme.TrophyTrackTheme
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

    TrophyTrackTheme {
        Scaffold { paddingValues ->
            DashboardScreen(paddingValues = paddingValues,container = container)
        }
    }
}

