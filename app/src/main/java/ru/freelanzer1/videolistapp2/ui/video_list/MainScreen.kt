package ru.freelanzer1.videolistapp2.ui.video_list

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.freelanzer1.videolistapp2.ui.video_list.components.OrderSection
import ru.freelanzer1.videolistapp2.ui.util.Screen
import ru.freelanzer1.videolistapp2.ui.util.playeer.VerticalVideoPager

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavController,
    viewModelAlbum: MainScreenViewModel = hiltViewModel(),
) {
    val state = viewModelAlbum.state.value
    val videoState = remember { viewModelAlbum.videoListState }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val addUpVideoList = { viewModelAlbum.addUpVideoList()}

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditAlbumScreen.route)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add album")
            }

        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {
            VerticalVideoPager(videos = videoState.toList(), addUpVideoList = addUpVideoList)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                //.padding(15.dp)
            ) {
                val bgColorL = if(state.isOrderSectionVisible)  MaterialTheme.colorScheme.background else Color.Transparent
                Row(
                    modifier = Modifier.fillMaxWidth().background(bgColorL),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Text(
//                        text = "Your video with tags",
//                        style = MaterialTheme.typography.bodyLarge
//                    )
                    IconButton(
                        onClick = {
                            viewModelAlbum.onEvent(AlbumsEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        albumOrder = state.albumOrder,
                        onOrderChange = {
                            viewModelAlbum.onEvent(AlbumsEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(state.albums) { album ->
//                    AlbumItem(
//                        album = album,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                navController.navigate(
//                                    Screen.AddEditAlbumScreen.route +
////                                            "?albumId=${album.id}&albumColor=${album.color}"
//                                            "?albumId=${album.id}"
//                                )
//                            },
//                        onDeleteClick = {
//                            viewModel.onEvent(AlbumsEvent.DeleteAlbum(album))
//                            scope.launch {
//                                val result = snackbarHostState.showSnackbar(
//                                    message = "Album deleted",
//                                    actionLabel = "Undo"
//                                )
//                                if(result == SnackbarResult.ActionPerformed) {
//                                    viewModel.onEvent(AlbumsEvent.RestoreAlbum)
//                                }
//                            }
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//            }

            }
        }
    }
}