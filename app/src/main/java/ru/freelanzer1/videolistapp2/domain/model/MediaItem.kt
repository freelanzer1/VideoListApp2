package ru.freelanzer1.videolistapp2.domain.model

import android.net.Uri

abstract class MediaItem{

    abstract val srvVideoId: Int?
    abstract val videoId: Int?
    abstract val srvUrl: String
    abstract val localUri: Uri?
    abstract val width: Int
    abstract val height: Int
    abstract val stats: Stats
    abstract val currentViewerInteraction: ViewerInteraction


}

data class Stats(
    var like: Long = 0,
    var comment: Long = 0
)

data class ViewerInteraction(
    var isLikedByYou: Boolean = false,
    var isAddedToFavourite: Boolean = false
)
