package ru.freelanzer1.videolistapp2.di

import android.app.Application
import androidx.room.Room
import ru.freelanzer1.videolistapp2.data.data_source.NoteDatabase
import ru.freelanzer1.videolistapp2.data.repository.NoteRepositoryImpl
import ru.freelanzer1.videolistapp2.domain.repository.NoteRepository
import ru.freelanzer1.videolistapp2.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.freelanzer1.videolistapp2.domain.use_case.AddNote
import ru.freelanzer1.videolistapp2.domain.use_case.DeleteNote
import ru.freelanzer1.videolistapp2.domain.use_case.GetNote
import ru.freelanzer1.videolistapp2.domain.use_case.GetNotes
import ru.freelanzer1.videolistapp2.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}