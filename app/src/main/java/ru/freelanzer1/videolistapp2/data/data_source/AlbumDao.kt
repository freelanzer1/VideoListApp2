package ru.freelanzer1.videolistapp2.data.data_source

import androidx.room.*
import ru.freelanzer1.videolistapp2.domain.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun getalbums(): Flow<List<Album>>

    @Query("SELECT * FROM album WHERE id = :id")
    suspend fun getalbumById(id: Int): Album?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertalbum(album: Album)

    @Delete
    suspend fun deletealbum(album: Album)
}