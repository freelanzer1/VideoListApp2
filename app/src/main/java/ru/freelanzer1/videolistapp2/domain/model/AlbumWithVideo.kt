package ru.freelanzer1.videolistapp2.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithVideo (
    @Embedded
    val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "videoId"
    )
    val videos: List<Video>
)