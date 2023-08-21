package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository
import ru.freelanzer1.videolistapp2.domain.util.AlbumOrder
import ru.freelanzer1.videolistapp2.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAlbums(
    private val repository: AlbumRepository
) {

    operator fun invoke(
        albumOrder: AlbumOrder = AlbumOrder.Date(OrderType.Descending)
    ): Flow<List<Album>> {
        return repository.getAlbums().map { albums ->
            when(albumOrder.orderType) {
                is OrderType.Ascending -> {
                    when(albumOrder) {
                        is AlbumOrder.Title -> albums.sortedBy { it.title.lowercase() }
                        is AlbumOrder.Date -> albums.sortedBy { it.timestamp }
                        is AlbumOrder.Color -> albums.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(albumOrder) {
                        is AlbumOrder.Title -> albums.sortedByDescending { it.title.lowercase() }
                        is AlbumOrder.Date -> albums.sortedByDescending { it.timestamp }
                        is AlbumOrder.Color -> albums.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}