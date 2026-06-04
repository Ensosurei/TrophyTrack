package org.ensosurei.trophytrack.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual class DatabaseBuilder() {
    actual fun createBuilder(): RoomDatabase.Builder<AppDatabase>{
        val userDirectory = System.getProperty("user.home")
        val userOS = System.getProperty("os.name")
        val finalDbPath : String
        if(userOS.lowercase().contains("win")){
            val dbFolderWin = File(userDirectory, "AppData\\Roaming\\TrophyTrack")
            dbFolderWin.mkdirs()
            finalDbPath = File(dbFolderWin,"trophytrack.db").absolutePath
        }else{
            val dbFolder = File(userDirectory, ".trophytrack")
            dbFolder.mkdirs()
            finalDbPath = File(dbFolder,"trophytrack.db").absolutePath
        }
        val builder = Room.databaseBuilder<AppDatabase>(name = finalDbPath)
        builder.setDriver(BundledSQLiteDriver())
        return builder
    }
}