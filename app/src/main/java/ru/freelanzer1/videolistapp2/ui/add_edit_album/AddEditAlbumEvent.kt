package ru.freelanzer1.videolistapp2.ui.add_edit_album

import androidx.compose.ui.focus.FocusState

sealed class AddEditAlbumEvent{
    data class EnteredTitle(val value: String): AddEditAlbumEvent()
    data class SelectItem(val item: Int, val selected: Boolean): AddEditAlbumEvent()
    object RemoveSelected: AddEditAlbumEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditAlbumEvent()

    //data class ChangeColor(val color: Int): AddEditAlbumEvent()
    object SaveAlbum: AddEditAlbumEvent()
}

