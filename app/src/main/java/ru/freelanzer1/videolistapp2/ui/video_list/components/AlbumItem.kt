package ru.freelanzer1.videolistapp2.ui.video_list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.freelanzer1.videolistapp2.domain.model.Album

@Composable
fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

//            clipPath(clipPath) {
//                drawRoundRect(
//                    //color = Color(album.color),
//                    size = size,
//                    cornerRadius = CornerRadius(cornerRadius.toPx())
//                )
////                drawRoundRect(
////                    color = Color(
////                        ColorUtils.blendARGB(album.color, 0x000000, 0.2f)
////                    ),
////                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
////                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
////                    cornerRadius = CornerRadius(cornerRadius.toPx())
////                )
//            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = album.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete album",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}