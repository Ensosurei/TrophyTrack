package org.ensosurei.trophytrack

import android.app.Application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.preferences.DataStoreFactory

class TrophyTrackApp : Application(){
    val androidBuilder by lazy { DatabaseBuilder(this) }
    val androidStore by lazy { DataStoreFactory(this) }
    val appContainer by lazy { AppContainer(androidStore,androidBuilder) }
}