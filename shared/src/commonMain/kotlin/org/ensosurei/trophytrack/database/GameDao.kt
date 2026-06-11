package org.ensosurei.trophytrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Upsert
    suspend fun saveGames(game: List<GameEntity>)

    @Upsert
    suspend fun saveGame(game: GameEntity)

    @Insert
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT * FROM GameEntity")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM GameEntity WHERE status='PLAYING'")
    fun getPlayingGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM GameEntity WHERE status='COMPLETED'")
    fun getCompletedGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM GameEntity WHERE title LIKE :searchQuery")
    fun searchGames(searchQuery: String): Flow<List<GameEntity>>
}