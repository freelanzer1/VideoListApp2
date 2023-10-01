package ru.freelanzer1.videolistapp2.ui.video_list

import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.util.AlbumOrder
import ru.freelanzer1.videolistapp2.domain.util.OrderType

data class AlbumsState(
    val albums: List<Album> = emptyList(),
    val albumOrder: AlbumOrder = AlbumOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
