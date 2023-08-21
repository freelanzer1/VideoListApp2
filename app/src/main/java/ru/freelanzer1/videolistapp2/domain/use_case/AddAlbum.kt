package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.InvalidAlbumException
import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository

class AddAlbum(
    private val repository: AlbumRepository
) {

    @Throws(InvalidAlbumException::class)
    suspend operator fun invoke(album: Album) {
        if(album.title.isBlank()) {
            throw InvalidAlbumException("The title of the album can't be empty.")
        }
        if(album.description.isBlank()) {
            throw InvalidAlbumException("The content of the album can't be empty.")
        }
        repository.insertAlbum(album)
    }
}