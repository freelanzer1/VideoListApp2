package ru.freelanzer1.videolistapp2.ui.add_edit_album

import android.net.Uri

data class AlbumMediaItemState(
    val mediaUri: Uri,
    val type: String?,
    val selected: Boolean = false
)
