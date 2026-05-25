package org.ensosurei.trophytrack.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "GameNotesEntity",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["gameId"])]
    )
data class GameNotesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val gameId: Int,
    val category: String,
    val content: String,
    val createdAt: Long
)
