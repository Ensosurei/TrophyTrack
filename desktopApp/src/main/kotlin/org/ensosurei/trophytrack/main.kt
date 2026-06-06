package org.ensosurei.trophytrack

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.network.RawgApiClient
import org.ensosurei.trophytrack.preferences.DataStoreFactory
import org.ensosurei.trophytrack.repository.GameRepository

fun main() = application {
    val dataBuiler = DatabaseBuilder()
    val store = DataStoreFactory()
    val database = dataBuiler.createBuilder().build()
    val gameDao = database.gameDao()
    val apiClient = RawgApiClient()
    val gameRepository = GameRepository(apiClient = apiClient, gameDao = gameDao)

    val appContainer = AppContainer(store,dataBuiler, gameRepository)
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrophyTrack",
    ) {
        App(container = appContainer)
    }
}