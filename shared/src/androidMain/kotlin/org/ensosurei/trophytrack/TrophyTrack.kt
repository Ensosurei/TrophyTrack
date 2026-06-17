package org.ensosurei.trophytrack

import android.app.Application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.network.RawgApiClient
import org.ensosurei.trophytrack.preferences.DataStoreFactory
import org.ensosurei.trophytrack.repository.GameRepository

class TrophyTrackApp : Application(){
    val appContainer by lazy {
        val builder = DatabaseBuilder(this)
        val store = DataStoreFactory(this)
        val database = builder.createBuilder().build()
        val gameDao = database.gameDao()
        val gameNotesDao = database.gameNotesDao()
        val apiClient = RawgApiClient()
        val gameRepository = GameRepository(apiClient = apiClient, gameDao = gameDao, gameNotesDao = gameNotesDao)


        AppContainer(store,builder, gameRepository)
    }
}