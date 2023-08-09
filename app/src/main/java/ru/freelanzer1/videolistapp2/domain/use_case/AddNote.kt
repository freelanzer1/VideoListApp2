package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.InvalidNoteException
import ru.freelanzer1.videolistapp2.domain.model.Note
import ru.freelanzer1.videolistapp2.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}