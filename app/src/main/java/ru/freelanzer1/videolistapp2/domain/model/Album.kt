package ru.freelanzer1.videolistapp2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Album(
    val title: String,
    val description: String,
    val timestamp: Long,
    //val color: Int,
    val srvId: String?,
    val authorId: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        //val albumColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidAlbumException(message: String): Exception(message)