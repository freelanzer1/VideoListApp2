package ru.freelanzer1.videolistapp2.di

import android.app.Application
import androidx.room.Room
import ru.freelanzer1.videolistapp2.data.data_source.AppDatabase
import ru.freelanzer1.videolistapp2.data.repository.AlbumRepositoryImpl
import ru.freelanzer1.videolistapp2.domain.repository.AlbumRepository
import ru.freelanzer1.videolistapp2.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.freelanzer1.videolistapp2.domain.use_case.AddAlbum
import ru.freelanzer1.videolistapp2.domain.use_case.DeleteAlbum
import ru.freelanzer1.videolistapp2.domain.use_case.GetAlbum
import ru.freelanzer1.videolistapp2.domain.use_case.GetAlbums
import ru.freelanzer1.videolistapp2.domain.use_case.AlbumUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(db: AppDatabase): AlbumRepository {
        return AlbumRepositoryImpl(db.albumDao)
    }

    @Provides
    @Singleton
    fun provideAlbumUseCases(repository: AlbumRepository): AlbumUseCases {
        return AlbumUseCases(
            getAlbums = GetAlbums(repository),
            deleteAlbum = DeleteAlbum(repository),
            addAlbum = AddAlbum(repository),
            getAlbum = GetAlbum(repository)
        )
    }
}