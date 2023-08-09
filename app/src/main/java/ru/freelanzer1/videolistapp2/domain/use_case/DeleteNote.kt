package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.Note
import ru.freelanzer1.videolistapp2.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}