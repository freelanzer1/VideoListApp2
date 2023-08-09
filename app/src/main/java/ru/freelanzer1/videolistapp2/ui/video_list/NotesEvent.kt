package ru.freelanzer1.videolistapp2.ui.video_list

import ru.freelanzer1.videolistapp2.domain.model.Note
import ru.freelanzer1.videolistapp2.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
