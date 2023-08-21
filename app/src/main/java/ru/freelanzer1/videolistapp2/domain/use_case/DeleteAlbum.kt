package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository

class DeleteAlbum(
    private val repository: AlbumRepository
) {

    suspend operator fun invoke(album: Album) {
        repository.deleteAlbum(album)
    }
}