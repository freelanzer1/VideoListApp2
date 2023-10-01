package ru.freelanzer1.videolistapp2.ui.util.playeer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freelanzer1.videolistapp2.domain.model.Video
import java.net.URL


@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayer(
    video: Video,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {},
    addUpVideoList: (() -> Unit)? = null,
    pageCount: Int
) {

    val context = LocalContext.current
    var thumbnail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))  //bitmap, isShow
    }
    var isFirstFrameLoad = remember { false }
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            val url = URL(video.thumb)
            val bm = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            val bm = FileUtils.extractThumbnail(
//                context.assets.openFd("videos/${video.videoLink}"), 1
//            )
            withContext(Dispatchers.Main) {
                thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

//    if (pagerState.settledPage == pageCount -1 && addUpVideoList != null){
//        addUpVideoList();
//    }
        if (pagerState.settledPage == pageIndex) {
            val exoPlayer = remember(context) {
                ExoPlayer.Builder(context).build().apply {
                    videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                    repeatMode = Player.REPEAT_MODE_ONE
                    //setMediaItem(MediaItem.fromUri(Uri.parse("asset:///videos/${video.videoLink}")))
                    setMediaItem(MediaItem.fromUri(Uri.parse(video.srvUrl)))
                    playWhenReady = true
                    prepare()
                    addListener(object : Player.Listener {
                        override fun onRenderedFirstFrame() {
                            super.onRenderedFirstFrame()
                            isFirstFrameLoad = true
                            thumbnail = thumbnail.copy(second = false)
                        }
                    })
                }
            }

            val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
            DisposableEffect(key1 = lifecycleOwner) {
                val lifeCycleObserver = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_STOP -> {
                            exoPlayer.pause()
                            onVideoGoBackground()
                        }
                        Lifecycle.Event.ON_START -> exoPlayer.play()
                        else -> {}
                    }
                }
                lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
                }
            }

            val playerView = remember {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }

            DisposableEffect(key1 = AndroidView(factory = {
                playerView
            }, modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onSingleTap(exoPlayer)
                }, onDoubleTap = { offset ->
                    onDoubleTap(exoPlayer, offset)
                })
            }), effect = {
                onDispose {
                    thumbnail = thumbnail.copy(second = true)
                    exoPlayer.release()
                    onVideoDispose()
                }
            })
        }
        if (thumbnail.second) {
            AsyncImage(
                model = thumbnail.first,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}