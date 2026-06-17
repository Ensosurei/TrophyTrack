package org.ensosurei.trophytrack.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilder(private val context: Context) {
    actual fun createBuilder() : RoomDatabase.Builder<AppDatabase>{
        val dbFile = context.getDatabasePath("trophytrack.db")
        return Room.databaseBuilder<AppDatabase>(context.applicationContext,dbFile.absolutePath).fallbackToDestructiveMigration(false)
    }
}