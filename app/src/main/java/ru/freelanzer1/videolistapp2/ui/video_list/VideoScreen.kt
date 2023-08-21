package ru.freelanzer1.videolistapp2.ui.video_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.freelanzer1.videolistapp2.ui.theme.VideoListAppTheme
import ru.freelanzer1.videolistapp2.ui.util.videos
import ru.freelanzer1.videolistapp2.ui.video_list.components.VideoList

@Composable
fun VideoScreen(
    goBack: () -> Unit
) {
    ExoPlayerScreenSkeleton(
        goBack = goBack
    )
}

@Preview
@Composable
fun ExoPlayerScreenSkeletonPreview() {
    VideoListAppTheme {
        ExoPlayerScreenSkeleton()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExoPlayerScreenSkeletonPreviewDark() {
    VideoListAppTheme {
        ExoPlayerScreenSkeleton()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExoPlayerScreenSkeleton(
    goBack: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
//            AppComponent.Header(
//                "ExoPlayer",
//                goBack = goBack
//            )

            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

            Divider()

            // ----------------------------------------------------------------

            VideoList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                videos = videos
            )
        }
    }
}

