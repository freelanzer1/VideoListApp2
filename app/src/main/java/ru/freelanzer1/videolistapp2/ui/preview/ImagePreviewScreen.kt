package ru.freelanzer1.videolistapp2.ui.preview

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.*
import coil.compose.AsyncImagePainter.State.*
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import ru.freelanzer1.videolistapp2.ui.theme.ExtendedTheme

import ru.freelanzer1.videolistapp2.ui.util.videos
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ImagePreviewScreen(imageUri: Uri) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
            }.crossfade(true)
            .build()

        val painter = rememberAsyncImagePainter(
            model = imageUri,
            imageLoader = imageLoader,
        )

        val imageState = painter.state

        if (imageState is Loading) {
            Box(
                modifier = Modifier
                    //.clip(shape = RoundedCornerShape(12.dp))
                    .background(color = Color.LightGray)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 2.dp
                )
            }
        }
        Image(
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                //.clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
//                .clickable {
//
//                }
        )
    }
}


@Preview
@Composable
fun ImagePreviewScreenPreviewa() {
    ExtendedTheme {
        ImagePreviewScreen(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"))
    }
}

