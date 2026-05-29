package org.ensosurei.trophytrack.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect class DataStoreFactory {
    fun createDataStoreFactory() : DataStore<Preferences>
}