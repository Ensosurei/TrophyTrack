package org.ensosurei.trophytrack.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual class DataStoreFactory {
    actual fun createDataStoreFactory() : DataStore<Preferences>{
        val userHome = System.getProperty("user.home")
        val userOS = System.getProperty("os.name")
        if(userOS.lowercase().contains("win")){
            val preferencesFileWin = File(userHome,"AppData\\Roaming\\TrophyTrack\\preferences.preferences_pb")
            preferencesFileWin.parentFile?.mkdirs()
            return PreferenceDataStoreFactory.create(produceFile = {preferencesFileWin})
        }
        val preferencesFile = File(userHome, ".trophytrack/preferences.preferences_pb")
        preferencesFile.parentFile?.mkdirs()
        return PreferenceDataStoreFactory.create(produceFile = {preferencesFile})
    }
}