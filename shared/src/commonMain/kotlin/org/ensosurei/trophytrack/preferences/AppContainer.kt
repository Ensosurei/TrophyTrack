package org.ensosurei.trophytrack.preferences

import org.ensosurei.trophytrack.database.DatabaseBuilder

class AppContainer(val storeFactory : DataStoreFactory, val dataBuilder: DatabaseBuilder) {
    val db by lazy { dataBuilder.createBuilder().build() }
    val store by lazy {storeFactory.createDataStoreFactory()}
    val userPreferences by lazy { UserPreferences(dataStore = store) }
}