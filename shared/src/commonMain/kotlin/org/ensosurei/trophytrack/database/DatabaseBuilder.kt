package org.ensosurei.trophytrack.database

import androidx.room.RoomDatabase

expect class DatabaseBuilder{
    fun createBuilder() : RoomDatabase.Builder<AppDatabase>
}