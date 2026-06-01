package org.ensosurei.trophytrack

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.preferences.DataStoreFactory

fun main(){
    val dataBuiler = DatabaseBuilder()
    val store = DataStoreFactory()
    val appContainer = AppContainer(store,dataBuiler)

    application {
        Window(onCloseRequest = ::exitApplication, title = "TrophyTrack"){
            App(container = appContainer)
        }
    }
}