package org.ensosurei.trophytrack.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class UserPreferences(private val dataStore : DataStore<Preferences>) {
    companion object PreferencesKeys{
        val steamId = stringPreferencesKey("steam_id")
        val steamName = stringPreferencesKey("steam_name")
        val steamAvatarUrl = stringPreferencesKey("steam_avatar_url")
        val localAvatarPath = stringPreferencesKey("local_avatar_path")
        val localName = stringPreferencesKey("local_name")
    }
}