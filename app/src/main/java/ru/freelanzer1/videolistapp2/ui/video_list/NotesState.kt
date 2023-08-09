package ru.freelanzer1.videolistapp2.ui.video_list

import ru.freelanzer1.videolistapp2.domain.model.Note
import ru.freelanzer1.videolistapp2.domain.util.NoteOrder
import ru.freelanzer1.videolistapp2.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
