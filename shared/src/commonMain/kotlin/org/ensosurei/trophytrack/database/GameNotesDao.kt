package org.ensosurei.trophytrack.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameNotesDao {
    @Upsert
    suspend fun saveNote(note: GameNotesEntity)

    @Query("SELECT * FROM GameNotesEntity WHERE gameId= :id")
    fun getNotes(id:Int): Flow<List<GameNotesEntity>>

    @Delete
    suspend fun deleteNote(note: GameNotesEntity)
}