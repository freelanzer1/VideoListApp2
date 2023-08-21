package ru.freelanzer1.videolistapp2.domain.repository

import ru.freelanzer1.videolistapp2.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    fun getAlbums(): Flow<List<Album>>

    suspend fun getAlbumById(id: Int): Album?

    suspend fun insertAlbum(album: Album)

    suspend fun deleteAlbum(album: Album)
}