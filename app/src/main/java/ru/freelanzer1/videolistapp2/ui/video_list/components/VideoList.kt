package ru.freelanzer1.videolistapp2.ui.video_list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.freelanzer1.videolistapp2.domain.model.Video

@Composable
private fun VideoList(
    modifier: Modifier,
    videos: List<Video>
) {
    val lazyListState = rememberLazyListState()
    // play the video on the first visible item in the list
    val focusIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    val focusIndexOffset by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    val density = LocalDensity.current

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp)
    ) {
        items(count = Int.MAX_VALUE) { index ->
            val videoIndex = index % videos.size

            VideoItem(
                video = videos[videoIndex],
                focusedVideo = (index == 0 && focusIndexOffset <= with(density) { 48.dp.toPx() }) ||
                        (index == focusIndex + 1 && focusIndexOffset > with(density) { 48.dp.toPx() })
            )
        }
    }
}