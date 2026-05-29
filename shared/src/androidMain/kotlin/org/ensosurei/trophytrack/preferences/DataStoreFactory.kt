package org.ensosurei.trophytrack.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual class DataStoreFactory(private val context : Context) {
    actual fun createDataStoreFactory() : DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(produceFile = {
            File(context.filesDir,"datastore/trophytrack.preferences_pb")
        })
    }
}