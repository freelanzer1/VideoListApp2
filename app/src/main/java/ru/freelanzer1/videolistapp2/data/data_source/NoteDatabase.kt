package ru.freelanzer1.videolistapp2.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.freelanzer1.videolistapp2.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}