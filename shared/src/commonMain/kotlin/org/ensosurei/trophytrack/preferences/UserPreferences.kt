package org.ensosurei.trophytrack.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val dataStore : DataStore<Preferences>) {
    companion object PreferencesKeys{
        val steamId = stringPreferencesKey("steam_id")
        val steamName = stringPreferencesKey("steam_name")
        val steamAvatarUrl = stringPreferencesKey("steam_avatar_url")
        val localAvatarPath = stringPreferencesKey("local_avatar_path")
        val localName = stringPreferencesKey("local_name")
    }
    val steamIdFlow : Flow<String> = dataStore.data.map { it[PreferencesKeys.steamId]?: "" }
    val steamNameFlow : Flow<String> = dataStore.data.map { it[PreferencesKeys.steamName]?: "" }
    val steamAvatarFlow : Flow<String> = dataStore.data.map { it[PreferencesKeys.steamAvatarUrl]?: "" }
    val localNameFlow : Flow<String> = dataStore.data.map { it[PreferencesKeys.localName]?: "Jugador Local" }
    val  localAvatarPathFlow : Flow<String> = dataStore.data.map { it[PreferencesKeys.localAvatarPath]?: "avatar_default" }

    suspend fun updateLocalProfile(newLocalName: String, newLocalAvatarPath: String){
        dataStore.edit { preferences ->
            preferences[localName] = newLocalName
            preferences[localAvatarPath] = newLocalAvatarPath
        }
    }

    suspend fun updateSteamProfile(newSteamId:String, newSteamName: String, newSteamAvatarUrl: String){
        dataStore.edit { preferences ->
            preferences[steamId] = newSteamId
            preferences[steamName] = newSteamName
            preferences[steamAvatarUrl] = newSteamAvatarUrl
        }
    }

    suspend fun clearLocalProfile(){
        dataStore.edit{ preferences ->
            preferences.remove(localName)
            preferences.remove(localAvatarPath)
        }
    }

    suspend fun clearSteamProfile(){
        dataStore.edit{ preferences ->
            preferences.remove(steamId)
            preferences.remove(steamName)
            preferences.remove(steamAvatarUrl)
        }
    }
}