package ru.freelanzer1.videolistapp2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.freelanzer1.videolistapp2.ui.theme.BabyBlue
import ru.freelanzer1.videolistapp2.ui.theme.LightGreen
import ru.freelanzer1.videolistapp2.ui.theme.RedOrange
import ru.freelanzer1.videolistapp2.ui.theme.RedPink
import ru.freelanzer1.videolistapp2.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    //Todo add video link
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)