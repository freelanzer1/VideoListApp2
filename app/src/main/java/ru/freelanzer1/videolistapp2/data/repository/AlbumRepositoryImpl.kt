package ru.freelanzer1.videolistapp2.data.repository


import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import ru.freelanzer1.videolistapp2.data.data_source.AlbumDao

class AlbumRepositoryImpl(
    private val dao: AlbumDao
) : AlbumRepository {

    override fun getAlbums(): Flow<List<Album>> {
        return dao.getalbums()
    }

    override suspend fun getAlbumById(id: Int): Album? {
        return dao.getalbumById(id)
    }

    override suspend fun insertAlbum(album: Album) {
        dao.insertalbum(album)
    }

    override suspend fun deleteAlbum(album: Album) {
        dao.deletealbum(album)
    }
}