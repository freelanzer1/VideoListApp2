package ru.freelanzer1.videolistapp2.domain.use_case

import ru.freelanzer1.videolistapp2.domain.model.Note
import ru.freelanzer1.videolistapp2.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}