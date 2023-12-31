package ru.freelanzer1.videolistapp2.domain.model

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    //val description: String,
    override val srvVideoId: Int?,
    @PrimaryKey
    override val videoId: Int?,
    override val srvUrl: String,
    override val localUri: Uri? = null,
    override val width: Int,
    override val height: Int,
    @Embedded
    override val stats: Stats = Stats(),
    @Embedded
    override val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val thumb: String,
    ) : MediaItem(){
    constructor(srvUrl: String, thumb: String, width: Int, height: Int ) : this(null, null, srvUrl, null, width, height, stats = Stats(), currentViewerInteraction = ViewerInteraction(), thumb)
    }




