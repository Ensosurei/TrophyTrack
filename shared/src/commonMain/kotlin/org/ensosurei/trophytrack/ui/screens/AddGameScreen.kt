package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.ensosurei.trophytrack.database.GameDao
import org.ensosurei.trophytrack.database.GameEntity
import org.ensosurei.trophytrack.ui.components.CategoryChip
import org.ensosurei.trophytrack.ui.theme.surface
import org.ensosurei.trophytrack.ui.theme.white
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_arrowBack
import trophytrack.shared.generated.resources.ic_camera
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(
    gameId: Int,
    gameDao: GameDao,
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit,
    modifier: Modifier
) {
    val gameFromDbFlow = remember(gameId) {
        gameDao.getAllGames().map { list -> list.find { it.id == gameId } }
    }
    val gameFromDb by gameFromDbFlow.collectAsState(initial = null)

    var titleText by remember { mutableStateOf("") }
    var coverUrlText by remember { mutableStateOf("") }
    var hoursText by remember { mutableStateOf("0") }
    var selectedStatus by remember { mutableStateOf("PLAYING") }
    var selectedPlatforms by remember { mutableStateOf(setOf<String>()) }
    var localImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    val isNewGame = remember { gameId == -1 }

    val scope = rememberCoroutineScope()
    val launcher = rememberFilePickerLauncher(type = PickerType.Image){ file ->
        if(file != null){
            scope.launch {
                localImageBytes = file.readBytes()
            }
        }
    }

    LaunchedEffect(gameFromDb){
        if (gameId != -1) {
            gameFromDb?.let { game ->
                titleText = game.title
                coverUrlText = game.coverUrl
                hoursText = game.hoursPlayed.toString()
                selectedStatus = game.status
                selectedPlatforms = game.platforms.split(", ").filter { it.isNotEmpty() }.toSet()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = surface)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_arrowBack),
                    contentDescription = null,
                    tint = white
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Add to Collection",
                style = MaterialTheme.typography.titleLarge,
                color = white
            )
        }
        if(localImageBytes != null){
            Card(
                modifier = Modifier
                    .width(180.dp)
                    .height(240.dp)
                    .clickable{launcher.launch()},
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                AsyncImage(
                    model = localImageBytes,
                    contentDescription = "New Local Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        } else if(coverUrlText.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .width(180.dp)
                    .height(240.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable{launcher.launch()}
                ){
                    AsyncImage(
                        model = coverUrlText,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
        else {
            Card(
                modifier = Modifier
                    .width(180.dp)
                    .height(240.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                        .clickable { launcher.launch() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_camera),
                            contentDescription = null
                        )
                        Text(
                            "Upload Image",
                            color = white,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Game title") },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = hoursText,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() || it == '.' } && newText.count { it == '.' } <= 1) {
                    hoursText = newText
                }
            },
            label = { Text("Played Hours") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        if (isNewGame || (gameFromDb?.origin ?: "MANUAL") == "MANUAL"){
            Text(
                text = "Platforms",
                color = white,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                val availablePlatforms = listOf("PC", "Playstation", "Xbox")
                availablePlatforms.forEach { platform ->
                   val isSelected = selectedPlatforms.contains(platform)
                    CategoryChip(
                        text = platform,
                        isSelected = isSelected,
                        onClick = {
                            selectedPlatforms = if(isSelected){
                                selectedPlatforms - platform
                            }else{
                                selectedPlatforms + platform
                            }
                        }
                    )
                }
            }
        }

        Text(
            text = "Select a status",
            style = MaterialTheme.typography.bodyMedium,
            color = white,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryChip(
                text = "Playing Now",
                isSelected = selectedStatus == "PLAYING",
                onClick = { selectedStatus = "PLAYING" },
                modifier = Modifier
                    .weight(1f)
            )
            CategoryChip(
                text = "Completed",
                isSelected = selectedStatus == "COMPLETED",
                onClick = { selectedStatus = "COMPLETED" },
                modifier = Modifier
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                var finalCover = coverUrlText
                val finalPlatforms = if (gameId == -1 || gameFromDb?.origin == "MANUAL") {
                    if (selectedPlatforms.isEmpty()) "PC" else selectedPlatforms.joinToString(", ")
                } else {
                    gameFromDb?.platforms
                }
                if(localImageBytes != null){
                   val savedPath = saveImageLocalStorage(localImageBytes!!)
                    finalCover = savedPath
                    coverUrlText = savedPath
                }

                val hours = hoursText.toFloatOrNull() ?: 0f;
                val actualTime = Clock.System.now().toEpochMilliseconds()
                val updateDate = if (isNewGame) actualTime else (gameFromDb?.addedAt ?: actualTime)
                val updatedGame = GameEntity(
                    id = if (isNewGame) 0 else gameId,
                    coverUrl = finalCover,
                    title = titleText,
                    hoursPlayed = hours,
                    status = selectedStatus,
                    platforms = finalPlatforms ?: "",
                    origin = if (gameId == -1) "MANUAL" else (gameFromDb?.origin ?: "MANUAL"),
                    externalId = if (gameId == -1) "" else (gameFromDb?.externalId ?: ""),
                    addedAt = updateDate,
                    updateAt = actualTime
                )
                scope.launch {
                    if (isNewGame) {
                        gameDao.insertGame(updatedGame)
                    } else {
                        gameDao.saveGame(updatedGame)
                    }
                    onSaveSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Save Collection")
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun saveImageLocalStorage(bytes: ByteArray): String {
    val fileName = "cover_${System.currentTimeMillis()}.png"
    val tmpdir = System.getProperty("java.io.tmpdir") ?: "."
    val targetFile = java.io.File(tmpdir,fileName)
    targetFile.writeBytes(bytes)
    return targetFile.absolutePath
}