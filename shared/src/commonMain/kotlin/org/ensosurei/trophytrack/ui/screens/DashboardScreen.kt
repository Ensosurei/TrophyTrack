package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ensosurei.trophytrack.AppContainer
import org.ensosurei.trophytrack.ui.components.BarNavigation
import org.ensosurei.trophytrack.ui.components.CategoryChip
import org.ensosurei.trophytrack.ui.components.GameCard
import org.ensosurei.trophytrack.ui.components.GameMiniCard
import org.ensosurei.trophytrack.ui.components.GamesGrid
import org.ensosurei.trophytrack.ui.components.SearchBar
import org.ensosurei.trophytrack.ui.theme.gray
import org.ensosurei.trophytrack.ui.theme.white
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_bell
import trophytrack.shared.generated.resources.ic_hamburguer
import trophytrack.shared.generated.resources.ic_profile


@Composable
fun DashboardScreen(
    paddingValues: PaddingValues,
    container: AppContainer
){
    var currentScreen by remember {mutableIntStateOf(0)}
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("All", "Steam", "PlayStation", "Xbox")
    val currentPlatform = categories[selectedCategoryIndex]
    val playingGames by container.db.gameDao().getPlayingGames().collectAsState(initial = emptyList())
    val completedGames by container.db.gameDao().getCompletedGames().collectAsState(initial = emptyList())
    val repository by lazy { container.gameRepository }
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val querySql = "%${searchQuery}%"
    val filterGames by container.db.gameDao().searchGames(querySql).collectAsState(initial = emptyList())

    BarNavigation(
        currentStatus = currentScreen,
        onStatusChange = { newScreen -> currentScreen = newScreen},
        onFabClick = {}
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = {}
                    ){
                        Icon(vectorResource(Res.drawable.ic_profile),
                            contentDescription = null)
                    }
                    Text(
                        text = "Home",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = white
                    )
                    Icon(
                        vectorResource(Res.drawable.ic_bell),
                        contentDescription = null
                    )
                }
                SearchBar(
                    query = searchQuery,
                    onQueryChange = {it ->
                        searchQuery = it
                        searchJob?.cancel()
                        searchJob = scope.launch {
                            delay(500)
                            if (searchQuery.isNotEmpty()){
                                repository.searchAndSyncGames(searchQuery)
                            }
                        }
                    }
                )
                if(searchQuery.isEmpty()){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ){
                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                itemsIndexed(categories){ index, category ->
                                    CategoryChip(
                                        text = category,
                                        isSelected = (selectedCategoryIndex == index),
                                        onClick = {selectedCategoryIndex = index}
                                    )
                                }
                            }
                        }

                        item{
                            Column {
                                Text(
                                    text = "Playing Now",
                                    fontSize = 18.sp,
                                    color = white,
                                    modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                                )

                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ){
                                    items(playingGames){ game ->
                                        GameCard(gameTitle = game.title, imageUrl = "")
                                    }
                                }
                            }
                        }

                        item {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        text = "Recently Completed",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = white
                                    )
                                    Text(
                                        text = "See All",
                                        fontSize = 18.sp,
                                        color = gray
                                    )
                                }

                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(completedGames){ game ->
                                        GameMiniCard(gameEntity = game)
                                    }
                                }
                            }
                        }
                    }
                }else {
                    GamesGrid(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        gameList = filterGames
                    )
                }
            }
        }
    }
}