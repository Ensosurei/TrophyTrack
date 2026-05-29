package org.ensosurei.trophytrack

import org.ensosurei.trophytrack.database.DatabaseBuilder
import org.ensosurei.trophytrack.preferences.DataStoreFactory
import org.ensosurei.trophytrack.preferences.UserPreferences

class AppContainer(val storeFactory : DataStoreFactory, val dataBuilder: DatabaseBuilder) {
    val db by lazy { dataBuilder.createBuilder().build() }
    val store by lazy {storeFactory.createDataStoreFactory()}
    val userPreferences by lazy { UserPreferences(dataStore = store) }
}