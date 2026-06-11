package org.ensosurei.trophytrack.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import org.ensosurei.trophytrack.AppContainer

@Composable
fun AppNavigation(
    container: AppContainer,
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ){
        composable("dashboard"){
            DashboardScreen(
                container = container,
                onNavigateToAddGame = {
                    navController.navigate("addGame")
                },
                onNavigateEditGame = { Id ->
                    navController.navigate("addGame?gameId=$Id")
                }
            )
        }

        composable(
            route = "addGame?gameId={gameId}",
            arguments = listOf(navArgument("gameId"){
                type = NavType.IntType
                defaultValue = -1
            })
        ){ backStackEntry ->
            val gameId = backStackEntry.arguments?.read {
                getInt("gameId")
            } ?: -1
            AddGameScreen(
                gameId = gameId,
                gameDao = container.db.gameDao(),
                onBack = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}