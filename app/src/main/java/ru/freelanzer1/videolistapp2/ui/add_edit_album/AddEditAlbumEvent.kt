package ru.freelanzer1.videolistapp2.ui.add_edit_album

import androidx.compose.ui.focus.FocusState

sealed class AddEditAlbumEvent{
    data class EnteredTitle(val value: String): AddEditAlbumEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditAlbumEvent()
    data class EnteredContent(val value: String): AddEditAlbumEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditAlbumEvent()
    data class ChangeColor(val color: Int): AddEditAlbumEvent()
    object SaveAlbum: AddEditAlbumEvent()
}

