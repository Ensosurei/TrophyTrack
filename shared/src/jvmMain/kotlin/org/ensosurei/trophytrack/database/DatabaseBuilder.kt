package org.ensosurei.trophytrack.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseBuilder() {
    actual fun createBuilder(): RoomDatabase.Builder<AppDatabase>{
        val userDirectory = System.getProperty("user.home")
        val userOS = System.getProperty("os.name")
        if(userOS.lowercase().contains("win")){
            val dbFolderWin = File(userDirectory, "AppData\\Roaming\\TrophyTrack")
            dbFolderWin.mkdirs()
            val dbFileWin = File(dbFolderWin,"trophytrack.db")
            return Room.databaseBuilder<AppDatabase>(name = dbFileWin.absolutePath)
        }
        val dbFolder = File(userDirectory, ".trophytrack")
        dbFolder.mkdirs()
        val dbFile = File(dbFolder,"trophytrack.db")
        return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
    }
}