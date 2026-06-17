package org.ensosurei.trophytrack.repository

import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import org.ensosurei.trophytrack.database.GameDao
import org.ensosurei.trophytrack.database.GameEntity
import org.ensosurei.trophytrack.database.GameNotesDao
import org.ensosurei.trophytrack.database.GameNotesEntity
import org.ensosurei.trophytrack.network.RawgApiClient
import org.ensosurei.trophytrack.network.RawgGameDto

class GameRepository(
    private val apiClient: RawgApiClient,
    private val gameDao: GameDao,
    private val gameNotesDao: GameNotesDao
) {
    suspend fun searchAndSyncGames(query: String){
        val apiResponse = apiClient.searchGames(query)

       val localGames: List<GameEntity> = apiResponse.results.map {
           GameEntity(
               id = it.id,
               title = it.name,
               coverUrl = it.background_image ?: "",
               platforms = it.platforms?.joinToString(separator = ", ") { platformDto -> platformDto.platform.name } ?: "PC",
               origin = "RAWG",
               externalId = null,
               hoursPlayed = 0.0f,
               status = "NONE",
               addedAt = Clock.System.now().toEpochMilliseconds(),
               updateAt = Clock.System.now().toEpochMilliseconds()
           )
       }
        gameDao.saveGames(localGames)
    }

    suspend fun getGameById(gameId: Int): GameEntity?{
        return gameDao.getGameById(gameId)
    }

    suspend fun deleteGameById(gameId: Int){
        gameDao.deleteGameById(gameId)
    }

    suspend fun updateGameStatus(gameId: Int, newStatus: String){
        gameDao.updateGameStatus(gameId, newStatus)
    }

    fun getGameNotes(gameId: Int): Flow<List<GameNotesEntity>> {
        return gameNotesDao.getNotes(gameId)
    }

    suspend fun saveGameNote(note: GameNotesEntity){
        gameNotesDao.saveNote(note)
    }

    suspend fun deleteGameNote(noteId: Int){
        gameNotesDao.deleteNote(noteId)
    }
}