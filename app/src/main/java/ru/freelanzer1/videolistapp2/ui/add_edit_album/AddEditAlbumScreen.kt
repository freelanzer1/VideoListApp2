package ru.freelanzer1.videolistapp2.ui.add_edit_album

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistRemove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.freelanzer1.videolistapp2.ui.add_edit_album.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import ru.freelanzer1.videolistapp2.ui.util.file_picker.FilePickerComponent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditAlbumScreen(
    navController: NavController,
    albumColor: Int = -1,
    viewModel: AddEditAlbumViewModel = hiltViewModel()
) {
    val titleState = viewModel.albumTitle.value
    val snackbarHostState = remember { SnackbarHostState() }

//    val albumBackgroundAnimatable = remember {
//        Animatable(
//            Color(if (albumColor != -1) albumColor else viewModel.albumColor.value)
//        )
//    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditAlbumViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditAlbumViewModel.UiEvent.SaveAlbum -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    modifier = Modifier
                        .navigationBarsPadding() // padding for navigation bar
                        .align(Alignment.BottomEnd)
                        .imePadding(), // padding for when IME appears
                    onClick = {
                        viewModel.onEvent(AddEditAlbumEvent.SaveAlbum)
                    },
                    shape = CircleShape,
                    //containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save album"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                //.padding(15.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding() // padding for the bottom for the IME
                .imeNestedScroll() // scroll IME at the bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                //.background(albumBackgroundAnimatable.value)
            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Album.albumColors.forEach { color ->
//                        val colorInt = color.toArgb()
//                        Box(
//                            modifier = Modifier
//                                .size(50.dp)
//                                .shadow(15.dp, CircleShape)
//                                .clip(CircleShape)
//                                .background(color)
//                                .border(
//                                    width = 3.dp,
//                                    color = if (viewModel.albumColor.value == colorInt) {
//                                        Color.Black
//                                    } else Color.Transparent,
//                                    shape = CircleShape
//                                )
//                                .clickable {
//                                    scope.launch {
//                                        albumBackgroundAnimatable.animateTo(
//                                            targetValue = Color(colorInt),
//                                            animationSpec = tween(
//                                                durationMillis = 500
//                                            )
//                                        )
//                                    }
//                                    viewModel.onEvent(AddEditAlbumEvent.ChangeColor(colorInt))
//                                }
//                        )
//                    }
//                }
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditAlbumEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditAlbumEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                FilePickerComponent( viewModel)
            }
        }
    }
}