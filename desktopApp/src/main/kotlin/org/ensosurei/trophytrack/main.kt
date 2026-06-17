package org.ensosurei.trophytrack

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.network.RawgApiClient
import org.ensosurei.trophytrack.network.RawgGameDto
import org.ensosurei.trophytrack.preferences.DataStoreFactory
import org.ensosurei.trophytrack.repository.GameRepository

fun main() = application {
    val dataBuilder = DatabaseBuilder()
    val store = DataStoreFactory()
    val database = dataBuilder.createBuilder().build()
    val gameDao = database.gameDao()
    val gameNotesDao = database.gameNotesDao()
    val apiClient = RawgApiClient()
    val gameRepository = GameRepository(apiClient = apiClient, gameDao = gameDao, gameNotesDao = gameNotesDao)

    val appContainer = AppContainer(store,dataBuilder, gameRepository)
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrophyTrack",
    ) {
        App(container = appContainer)
    }
}