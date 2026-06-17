package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil3.compose.AsyncImage
import org.ensosurei.trophytrack.ui.theme.surface
import org.ensosurei.trophytrack.ui.theme.surfaceVariant
import org.ensosurei.trophytrack.ui.theme.white
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_arrowBack

@Composable
fun GameDetailScreen(
    gameTitle: String,
    platform: String,
    inInLibrary: Boolean,
    coverImageUrl: String,
    onBackClick: () -> Unit,
    onAddToLibrary: () -> Unit,
    onEditGame: () -> Unit,
    onDelete: () -> Unit
){
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = if(inInLibrary) listOf("Info", "Notes") else listOf("Info")

    Scaffold(
        containerColor = surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ){
                AsyncImage(
                    model = coverImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    surface.copy(alpha = 0.5f),
                                    surface
                                )
                            )
                        )
                )

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .size(40.dp)
                        .background(surface.copy(0.4f), shape = CircleShape)
                ){
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrowBack),
                        contentDescription = null
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = surfaceVariant,
                ){
                    Text(
                        text = platform,
                        color = white,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = gameTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = white,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = if(inInLibrary) onEditGame else onAddToLibrary,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = surfaceVariant,
                        contentColor = white
                    )
                ){
                    Text(
                        text = if(inInLibrary) "Edit Game" else "Add to Library",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = white,
                divider = {
                    HorizontalDivider(color = surface)
                }
            ){
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {selectedTab = index},
                        text = {
                            Text(
                                text = title,
                                color = if(selectedTab == index) surfaceVariant else white,
                                fontWeight = if(selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when(selectedTab){
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        if(inInLibrary){
                            Text(
                                text = "This game is part of your library",
                                color = white.copy(0.7f),
                                fontSize = 14.sp
                            )
                        }else{
                            Text(
                                text = "This game is not part of your library",
                                color = white.copy(0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                1 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),

                    ){
                        Text(
                            text = "This is the notes section",
                            color = white.copy(0.7f),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
