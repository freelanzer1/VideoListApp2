package ru.freelanzer1.videolistapp2.ui.single_album

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.freelanzer1.videolistapp2.domain.model.Video
import ru.freelanzer1.videolistapp2.domain.use_case.AlbumUseCases
import ru.freelanzer1.videolistapp2.ui.util.videos
import javax.inject.Inject

@HiltViewModel
class SingleListViewModel @Inject constructor(
    private val albumUseCases: AlbumUseCases
) : ViewModel() {

     val state = mutableStateListOf<Video>() //ToDo remove to collectAsState? or collectAsStateWithLifecycle

    fun fetchVideos() {
        viewModelScope.launch {
            val randomPosition =  (0..videos.size-2).random()
            state.addAll(videos.subList(fromIndex = randomPosition, toIndex = randomPosition +2))
        }
    }
}