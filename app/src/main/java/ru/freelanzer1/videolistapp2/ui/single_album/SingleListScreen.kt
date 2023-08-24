package ru.freelanzer1.videolistapp2.ui.single_album

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.freelanzer1.videolistapp2.ui.theme.VideoListAppTheme
import ru.freelanzer1.videolistapp2.ui.util.playeer.VerticalVideoPager


@Composable
fun VideoListScreen(

    viewModel: SingleListViewModel = hiltViewModel<SingleListViewModel>()
) {
    // State
    val videoState = remember { viewModel.state }
    var refreshCount by remember { mutableStateOf(1) }

    // API call
    LaunchedEffect(key1 = refreshCount) {
        viewModel.fetchVideos()
    }

    val updateVM = { viewModel.fetchVideos()}


    VideoListAppTheme {
        VerticalVideoPager(videos = videoState.toList(), addUpVideoList =  updateVM)
    }
}
