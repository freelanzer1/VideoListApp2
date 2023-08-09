package ru.freelanzer1.videolistapp2.ui.video_list.components

import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.NonDisposableHandle.parent
import ru.freelanzer1.videeolistapp2.R
import ru.freelanzer1.videolistapp2.domain.model.Video
import ru.freelanzer1.videolistapp2.ui.util.exo_player.DataSourceHolder
import ru.freelanzer1.videolistapp2.ui.util.exo_player.PlayerViewPool
import ru.freelanzer1.videolistapp2.ui.util.exo_player.SimpleExoPlayerHolder


@Composable
fun VideoItem(video: Video, focusedVideo: Boolean) {
    val animateBackground by animateColorAsState(
        targetValue = if (focusedVideo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        label = "animateBackground"
    )

    Card(
        modifier = Modifier.padding(horizontal = 16.dp, 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = animateBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .aspectRatio(video.width.toFloat() / video.height.toFloat())
            ) {
//                Image(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.onSurface.copy(.1f)),
//                    painter = rememberImagePainter(data = video.thumb),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop
//                )

                Player(
                    modifier = Modifier.fillMaxSize(),
                    video = video,
                    focusedVideo = focusedVideo
                )

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp),
                    visible = focusedVideo,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp),
                        painter = painterResource(R.drawable.ic_round_play_arrow),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 0.dp),
                text = video.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 6.dp),
                text = video.subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = LocalContentColor.current.copy(.6f)
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
private fun Player(modifier: Modifier = Modifier, video: Video, focusedVideo: Boolean) {
    val context = LocalContext.current
    val exoPlayer = remember { SimpleExoPlayerHolder.get(context) }
    var playerView: PlayerView? = null

    if (focusedVideo) {
        LaunchedEffect(video.url) {
            // Start playing current video.
            val videoUri = Uri.parse(video.url)
            val dataSourceFactory = DataSourceHolder.getCacheFactory(context)
            val type = Util.inferContentType(videoUri)
            val source = when (type) {
                C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))

                C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))

                else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))
            }
            exoPlayer.setMediaSource(source)
            exoPlayer.prepare()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val frameLayout = FrameLayout(ctx)
            frameLayout
        },
        update = { frameLayout ->
            frameLayout.removeAllViews()
            if (focusedVideo) {
                playerView = PlayerViewPool.get(frameLayout.context)
                PlayerView.switchTargetView(
                    exoPlayer,
                    PlayerViewPool.currentPlayerView,
                    playerView
                )
                PlayerViewPool.currentPlayerView = playerView
                playerView?.apply {
                    player?.playWhenReady = true
                }

                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                }
                frameLayout.addView(
                    playerView,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            } else if (playerView != null) {
                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                    PlayerViewPool.release(this)
                }
                playerView = null
            }
        }
    )

    DisposableEffect(video.url) {
        onDispose {
            if (focusedVideo) {
                playerView?.apply {
                    (parent as? ViewGroup)?.removeView(this)
                }
                exoPlayer.stop()
                playerView?.let {
                    PlayerViewPool.release(it)
                }
                playerView = null
            }
        }
    }
}