package ru.freelanzer1.videolistapp2.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    //val description: String,
    val srvVideoId: String = "",
    val url: String,
    //val subtitle: String,
    val thumb: String,
    //val title: String,
    val width: Int,
    val height: Int,
    @Embedded val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    @Embedded val videoStats: VideoStats = VideoStats(),

    @PrimaryKey val videoId: Int? = null
)

data class VideoStats(
    var like: Long = 0,
    var comment: Long = 0
)

data class ViewerInteraction(
    var isLikedByYou: Boolean = false,
    var isAddedToFavourite: Boolean = false
)
