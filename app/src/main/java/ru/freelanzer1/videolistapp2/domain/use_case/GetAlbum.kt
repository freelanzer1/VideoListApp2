package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository

class GetAlbum(
    private val repository: AlbumRepository
) {

    suspend operator fun invoke(id: Int): Album? {
        return repository.getAlbumById(id)
    }
}