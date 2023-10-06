package ru.freelanzer1.videolistapp2.ui.util.file_picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import ru.freelanzer1.videolistapp2.ui.add_edit_album.AlbumMediaItemState
import ru.freelanzer1.videolistapp2.ui.video_list.components.DefaultRadioButton


@Composable
fun FilePickerComponent(addedFileUris:  List<AlbumMediaItemState>, addUrls: (List<Pair<Uri, String?>>)-> Unit, selectItem: (Int, Boolean) -> Unit) {
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
            addUrls.invoke(urisList)
        }
    )

    Column (
        //modifier = Modifier.background(Color.White)
    ){
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
            }else{
                Text("Add more files")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (addedFileUris.isNotEmpty()) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(addedFileUris) { index, itemState: AlbumMediaItemState ->
                    if ((itemState.type != null) && itemState.type.contains("image")) {
                        PhotoPreview(itemState.mediaUri, index, selectItem, activateCheckbox)
                    } else {
                        VideoPreview(uri = itemState.mediaUri, index, selectItem, activateCheckbox)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoPreview(uri: Uri, currentItem: Int, selectItem: (Int, Boolean) -> Unit, activateCheckbox: MutableState<Boolean>){
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
                        selectItem.invoke(currentItem, !checkedState.value)
                    }else{
                        //Todo clear all Checkbox
                        //checkedState.value = false
                        //selectItem.invoke(currentItem, false)
                    }
                },
                onLongClick = {
                    activateCheckbox.value = !activateCheckbox.value
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
                modifier = Modifier.size(30.dp).padding(3.dp),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    selectItem.invoke(currentItem, it)
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
fun VideoPreview(uri: Uri, currentItem: Int, selectItem: (Int, Boolean) -> Unit, activateCheckbox: MutableState<Boolean>){
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
                        selectItem.invoke(currentItem, !checkedState.value)
                    }else{
                        //Todo clear all Checkbox
                        //checkedState.value = false
                        //selectItem.invoke(currentItem, false)
                    }
                },
                onLongClick = {
                    activateCheckbox.value = !activateCheckbox.value
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
                modifier = Modifier.size(30.dp).padding(3.dp),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    selectItem.invoke(currentItem, it)
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White
                )
            )
        }
    }
}