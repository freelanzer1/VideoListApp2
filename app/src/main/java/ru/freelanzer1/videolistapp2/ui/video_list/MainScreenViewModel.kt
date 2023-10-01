package ru.freelanzer1.videolistapp2.ui.video_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.freelanzer1.videolistapp2.domain.model.Album
import ru.freelanzer1.videolistapp2.domain.use_case.AlbumUseCases
import ru.freelanzer1.videolistapp2.domain.util.AlbumOrder
import ru.freelanzer1.videolistapp2.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.freelanzer1.videolistapp2.domain.model.MediaItem
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val albumUseCases: AlbumUseCases
) : ViewModel() {

    private val _state = mutableStateOf(AlbumsState())
    val state: State<AlbumsState> = _state

    private val _mediaListState = mutableStateListOf<MediaItem>() //ToDo remove to collectAsState? or collectAsStateWithLifecycle
    val mediaListState: List<MediaItem> = _mediaListState



    private var recentlyDeletedAlbum: Album? = null

    private var getAlbumsJob: Job? = null

    private var getVideosJob: Job? = null

    init {
        getAlbums(AlbumOrder.Date(OrderType.Descending))
        addUpMediaList()
    }

    fun onEvent(event: AlbumsEvent) {
        when (event) {
            is AlbumsEvent.Order -> {
                if (state.value.albumOrder::class == event.albumOrder::class &&
                    state.value.albumOrder.orderType == event.albumOrder.orderType
                ) {
                    return
                }
                getAlbums(event.albumOrder)
            }
            is AlbumsEvent.DeleteAlbum -> {
                viewModelScope.launch {
                    albumUseCases.deleteAlbum(event.album)
                    recentlyDeletedAlbum = event.album
                }
            }
            is AlbumsEvent.RestoreAlbum -> {
                viewModelScope.launch {
                    albumUseCases.addAlbum(recentlyDeletedAlbum ?: return@launch)
                    recentlyDeletedAlbum = null
                }
            }
            is AlbumsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAlbums(albumOrder: AlbumOrder) {
        getAlbumsJob?.cancel()
        getAlbumsJob = albumUseCases.getAlbums(albumOrder)
            .onEach { albums ->
                _state.value = state.value.copy(
                    albums = albums,
                    albumOrder = albumOrder
                )
            }
            .launchIn(viewModelScope)
    }

    fun addUpMediaList() {
        getVideosJob?.cancel()
        getVideosJob = albumUseCases.getRandomVideos()
            .onEach { items ->
                _mediaListState.addAll(items)
            }
            .launchIn(viewModelScope)
    }
}