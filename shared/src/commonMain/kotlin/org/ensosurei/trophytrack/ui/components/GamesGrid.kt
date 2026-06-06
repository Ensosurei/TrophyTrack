package org.ensosurei.trophytrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ensosurei.trophytrack.database.GameEntity
import org.ensosurei.trophytrack.ui.theme.surface
import org.ensosurei.trophytrack.ui.theme.white

@Composable
fun GamesGrid(
    modifier: Modifier = Modifier,
    gameList : List<GameEntity>,
    onGameSelected: (GameEntity) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),

        modifier = modifier.fillMaxSize()
    ){
        items(gameList){ game ->
            GameCard(
                gameTitle = game.title,
                imageUrl = game.coverUrl,
                modifier = Modifier
                    .fillMaxWidth(),
                onPlayClick = {onGameSelected(game)}
            )
        }
    }
}