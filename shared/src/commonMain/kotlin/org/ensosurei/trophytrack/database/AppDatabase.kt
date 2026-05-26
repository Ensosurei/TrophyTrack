package org.ensosurei.trophytrack.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class, GameNotesEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameNotesDao(): GameNotesDao
}