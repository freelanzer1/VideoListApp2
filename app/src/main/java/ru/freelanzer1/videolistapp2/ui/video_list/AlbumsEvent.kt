package ru.freelanzer1.videolistapp2.ui.video_list

import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.util.AlbumOrder

sealed class AlbumsEvent {
    data class Order(val albumOrder: AlbumOrder): AlbumsEvent()
    data class DeleteAlbum(val album: Album): AlbumsEvent()
    object RestoreAlbum: AlbumsEvent()
    object ToggleOrderSection: AlbumsEvent()
}
