package ru.freelanzer1.videolistapp2.ui.add_edit_album

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.freelanzer1.videolistapp2.domain.model.InvalidAlbumException
import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.use_case.AlbumUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditAlbumViewModel @Inject constructor(
    private val albumUseCases: AlbumUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _albumTitle = mutableStateOf(
        AlbumTextFieldState(
        hint = "Enter album title..."
    )
    )
    val albumTitle: State<AlbumTextFieldState> = _albumTitle

    private val _albumContent = mutableStateOf(
        AlbumTextFieldState(
        hint = "Enter description"
    )
    )
    val albumContent: State<AlbumTextFieldState> = _albumContent


    private val _addedFileUris = mutableStateListOf<AlbumMediaItemState>()
    val addedFileUris: List<AlbumMediaItemState> = _addedFileUris
    fun addElements(items: List<Pair<Uri, String?>>) {
        val itemStateList: List<AlbumMediaItemState> = items.map { AlbumMediaItemState(it.first, it.second)}
        _addedFileUris.addAll(itemStateList)
    }

    //private val _albumColor = mutableStateOf(Album.albumColors.random().toArgb())
    //val albumColor: State<Int> = _albumColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentAlbumId: Int? = null

    init {
        savedStateHandle.get<Int>("albumId")?.let { albumId ->
            if(albumId != -1) {
                viewModelScope.launch {
                    albumUseCases.getAlbum(albumId)?.also { album ->
                        currentAlbumId = album.id
                        _albumTitle.value = albumTitle.value.copy(
                            text = album.title,
                            isHintVisible = false
                        )
                        _albumContent.value = _albumContent.value.copy(
                            text = album.description,
                            isHintVisible = false
                        )
                        //_albumColor.value = album.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditAlbumEvent) {
        when(event) {
            is AddEditAlbumEvent.EnteredTitle -> {
                _albumTitle.value = albumTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditAlbumEvent.SelectItem -> {
                _addedFileUris[event.item] = _addedFileUris[event.item].copy(selected = event.selected)

                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = "Selected item ${event.item}"
                        )
                    )
                }
            }
            is AddEditAlbumEvent.ChangeTitleFocus -> {
                _albumTitle.value = albumTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            albumTitle.value.text.isBlank()
                )
            }
            is AddEditAlbumEvent.EnteredContent -> {
                _albumContent.value = _albumContent.value.copy(
                    text = event.value
                )
            }
            is AddEditAlbumEvent.ChangeContentFocus -> {
                _albumContent.value = _albumContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _albumContent.value.text.isBlank()
                )
            }
//            is AddEditAlbumEvent.ChangeColor -> {
//                _albumColor.value = event.color
//            }
            is AddEditAlbumEvent.SaveAlbum -> {
                viewModelScope.launch {
                    try {
                        albumUseCases.addAlbum(
                            Album(
                                title = albumTitle.value.text,
                                description = albumContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                //color = albumColor.value,
                                id = currentAlbumId,
                                authorId = "0",
                                srvId = null
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveAlbum)
                    } catch(e: InvalidAlbumException) {
                        //ToDo check it
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save album"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveAlbum: UiEvent()
    }
}