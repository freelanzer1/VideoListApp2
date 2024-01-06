package ru.freelanzer1.videolistapp2.ui.util.file_picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.SpringDragCancelledAnimation
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable
import ru.freelanzer1.videolistapp2.ui.add_edit_album.AddEditAlbumEvent
import ru.freelanzer1.videolistapp2.ui.add_edit_album.AddEditAlbumViewModel
import ru.freelanzer1.videolistapp2.ui.add_edit_album.AlbumMediaItemState


@Composable
fun FilePickerComponent(viewModel: AddEditAlbumViewModel) {
    val addedFileUris = remember { viewModel.addedFileUris }
    val selectedItems = viewModel.addedFileUris.filter { it.selected }

    val doSelectItem = { pos: Int, selected: Boolean ->
        viewModel.onEvent(AddEditAlbumEvent.SelectItem(pos, selected))
    }

    val contentResolver = LocalContext.current.contentResolver
    val activateCheckbox = remember { mutableStateOf(false) }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        //Todo set maxItems to constant
        contract = ActivityResultContracts.PickMultipleVisualMedia(30),
        onResult = { uris ->
            val urisList: List<Pair<Uri, String?>> = uris.map { uri: Uri ->
                val type = contentResolver.getType(uri)
               return@map Pair(uri, type)
             }
            viewModel.addElements(urisList)
        }
    )

    Column {
        Row (
           // modifier = Modifier.background(Color.Green)
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                onClick = {
                    //On button press, launch the photo picker
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                }
            ) {
                if (addedFileUris.isEmpty()) {
                    //Text("Select files", style = MaterialTheme.typography.bodyMedium)
                    Text("Select files")
                } else {
                    Text("Add more files")
                }
            }
            if (selectedItems.isNotEmpty()) {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    onClick = {
                        viewModel.onEvent(AddEditAlbumEvent.RemoveSelected)
                        activateCheckbox.value = false
                    }
                ) {
                    Text("Remove selected")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (addedFileUris.isNotEmpty()) {
            var state = rememberReorderableLazyGridState(dragCancelledAnimation = NoDragCancelledAnimation(),
                onMove = { from, to ->
                    viewModel.replaceElements(from, to)
                })

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                state = state.gridState,
                modifier = Modifier.reorderable(state)
            ) {
                itemsIndexed(addedFileUris) { index, itemState: AlbumMediaItemState ->
                    ReorderableItem(state, key = index, defaultDraggingModifier = Modifier) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp,
                            label = index.toString()
                        )
                        Box(modifier = Modifier
                            .shadow(elevation.value)
                            .aspectRatio(1f)
                            .detectReorder(state)) {
                            if ((itemState.type != null) && itemState.type.contains("image")) {
                                PhotoPreview(
                                    itemState.mediaUri,
                                    index,
                                    doSelectItem,
                                    activateCheckbox
                                )
                            } else {
                                VideoPreview(
                                    uri = itemState.mediaUri,
                                    index,
                                    doSelectItem,
                                    activateCheckbox
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoPreview(uri: Uri, currentItem: Int, doSelectItem: (Int, Boolean) -> Unit, activateCheckbox: MutableState<Boolean>){
    val checkedState = remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = uri)
            .build()
    )
    Box(
        modifier = Modifier
            .background(Color.Black)
            .width(128.dp)
            .height(128.dp)
            .combinedClickable(
                onClick = {
                    if (activateCheckbox.value) {
                        checkedState.value = !checkedState.value
                        doSelectItem.invoke(currentItem, checkedState.value)
                    } else {
                        //Todo clear all Checkbox
                        //checkedState.value = false
                        //selectItem.invoke(currentItem, false)
                    }
                },
                onLongClick = {
                    activateCheckbox.value = !activateCheckbox.value
                    if (activateCheckbox.value) {
                        checkedState.value = true
                        doSelectItem.invoke(currentItem, true)
                    }
                },
                onDoubleClick = {

                },
            ),
    ){
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                //.padding(5.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.End) {
        Icon(
            modifier = Modifier.size(size = 30.dp),
            tint = Color.White,
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = "Photo"
        )
    }
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.Start) {
        if (activateCheckbox.value) {
            Checkbox(
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    doSelectItem.invoke(currentItem, it)
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPreview(uri: Uri, currentItem: Int, doSelectItem: (Int, Boolean) -> Unit, activateCheckbox: MutableState<Boolean>){
    val checkedState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }.crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = uri,
        imageLoader = imageLoader,
    )

    val imageState = painter.state

    if (imageState is AsyncImagePainter.State.Loading) {
        Box(
            modifier = Modifier
                //.clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .height(128.dp),
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
            .height(128.dp)
            .combinedClickable(
                onClick = {
                    if (activateCheckbox.value) {
                        checkedState.value = !checkedState.value
                        doSelectItem.invoke(currentItem, checkedState.value)
                    } else {
                        //Todo clear all Checkbox
                        //checkedState.value = false
                        //selectItem.invoke(currentItem, false)
                    }
                },
                onLongClick = {
                    activateCheckbox.value = !activateCheckbox.value
                    if (activateCheckbox.value) {
                        checkedState.value = true
                        doSelectItem.invoke(currentItem, true)
                    }
                },
                onDoubleClick = {

                },
            ),
    )
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.End) {
        Icon(
            modifier = Modifier.size(size = 30.dp),
            tint = Color.White,
            imageVector = Icons.Default.OndemandVideo,
            contentDescription = "Video file"
        )
    }
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.Start) {
        if (activateCheckbox.value) {
            Checkbox(
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    doSelectItem.invoke(currentItem, it)
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White
                )
            )
        }
    }
}