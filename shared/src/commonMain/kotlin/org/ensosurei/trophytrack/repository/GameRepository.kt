package org.ensosurei.trophytrack.repository

import org.ensosurei.trophytrack.database.GameDao
import org.ensosurei.trophytrack.database.GameEntity
import org.ensosurei.trophytrack.network.RawgApiClient
import org.ensosurei.trophytrack.network.RawgGameDto

class GameRepository(
    private val apiClient: RawgApiClient,
    private val gameDao: GameDao,
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
               addedAt = System.currentTimeMillis(),
               updateAt = System.currentTimeMillis()
           )
       }
        gameDao.saveGames(localGames)
    }
}