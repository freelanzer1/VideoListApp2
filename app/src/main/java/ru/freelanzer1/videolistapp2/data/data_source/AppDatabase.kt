package ru.freelanzer1.videolistapp2.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.freelanzer1.videolistapp2.domain.model.Album

@Database(
    entities = [Album::class],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {

    abstract val albumDao: AlbumDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}