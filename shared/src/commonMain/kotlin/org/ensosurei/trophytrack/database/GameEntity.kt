package org.ensosurei.trophytrack.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey val id : Int,
    val title : String,
    val coverUrl : String,
    val platforms : String,
    val origin : String,
    val externalId : String?,
    var hoursPlayed : Float,
    var status : String,
    val addedAt : Long,
    val updateAt : Long
)