package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.ensosurei.trophytrack.AppContainer
import org.ensosurei.trophytrack.database.GameEntity

@Composable
fun AppNavigation(
    container: AppContainer,
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DashboardRoute,
        modifier = modifier
    ){
        composable<DashboardRoute>{
            DashboardScreen(
                container = container,
                onNavigateToAddGame = {
                    navController.navigate(AddGameRoute())
                },
                onNavigateToDetail = { id ->
                    navController.navigate(GameDetailRoute(id))
                }
            )
        }

        composable<AddGameRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<AddGameRoute>()
            AddGameScreen(
                gameId = route.gameId,
                gameDao = container.db.gameDao(),
                onBack = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        composable<GameDetailRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<GameDetailRoute>()
            var gameEntity by remember { mutableStateOf<GameEntity?>(null) }
            LaunchedEffect(route.gameId){
                gameEntity = container.gameRepository.getGameById(route.gameId)
            }
            gameEntity?.let { game ->
                val inLibrary = game.status != "NONE"
                GameDetailScreen(
                    gameTitle = game.title,
                    platform = game.platforms,
                    inInLibrary = inLibrary,
                    coverImageUrl = game.coverUrl,
                    onBackClick = { navController.popBackStack() },
                    onAddToLibrary = {navController.navigate(AddGameRoute(gameId = game.id))},
                    onEditGame = {navController.navigate(AddGameRoute(gameId = game.id))},
                    onDelete = {navController.popBackStack()}
                )
            }
        }
    }
}

@Serializable
object DashboardRoute

@Serializable
data class AddGameRoute(val gameId: Int = -1)

@Serializable
data class GameDetailRoute(val gameId: Int)