package org.ensosurei.trophytrack

import android.app.Application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.preferences.DataStoreFactory

class TrophyTrackApp : Application(){
    val appContainer by lazy {
        val builder = DatabaseBuilder(this)
        val store = DataStoreFactory(this)

        AppContainer(store,builder)
    }
}