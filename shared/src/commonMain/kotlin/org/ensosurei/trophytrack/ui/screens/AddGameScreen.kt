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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toLong
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.ensosurei.trophytrack.database.GameDao
import org.ensosurei.trophytrack.database.GameEntity
import org.ensosurei.trophytrack.ui.components.CategoryChip
import org.ensosurei.trophytrack.ui.theme.gray
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
    game: GameEntity?,
    gameDao: GameDao,
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit,
    modifier: Modifier
) {
    var titleText by remember { mutableStateOf(game?.title ?: "") }
    var coverUrlText by remember { mutableStateOf(game?.coverUrl ?: "") }
    var hoursText by remember { mutableStateOf("0.0") }
    var selectedStatus by remember { mutableStateOf("PLAYING") }
    var showUrlInput by remember { mutableStateOf(false) }
    var selectedPlatforms by remember { mutableStateOf(setOf<String>()) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add to Collection",
                style = MaterialTheme.typography.titleLarge,
                color = white
            )
        }

        if (coverUrlText.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .width(180.dp)
                    .height(240.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                AsyncImage(
                    model = coverUrlText,
                    contentDescription = " Game Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp, 200.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                        .clickable { showUrlInput = !showUrlInput },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_camera),
                            contentDescription = null
                        )
                        Text("Upload Image", color = gray)
                    }
                }
                if (showUrlInput) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = coverUrlText,
                        onValueChange = { coverUrlText = it },
                        label = { Text("Pegar URL de la imagen") },
                        modifier = Modifier.fillMaxWidth()
                    )
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

        if(game?.origin == "MANUAL"){
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
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val finalPlatforms = if(game?.origin == "MANUAL"){
                    if(selectedPlatforms.isEmpty()) "PC" else selectedPlatforms.joinToString(", ")
                }else{
                    game?.platforms
                }

                val hours = hoursText.toFloatOrNull() ?: 0f;
                val actualTime = Clock.System.now().toEpochMilliseconds()
                val updateDate = game?.addedAt ?: actualTime
                val updatedGame = GameEntity(
                    id = game?.id ?: 0,
                    coverUrl = coverUrlText,
                    title = titleText,
                    hoursPlayed = hours,
                    status = selectedStatus,
                    platforms = finalPlatforms ?: "",
                    origin = game?.origin ?: "RAWG",
                    externalId = game?.externalId ?: "",
                    addedAt = updateDate,
                    updateAt = actualTime
                )
                scope.launch {
                    gameDao.saveGame(updatedGame)
                    onSaveSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Save Collection")
        }
    }
}