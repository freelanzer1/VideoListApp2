package ru.freelanzer1.videolistapp2.ui.video_list

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.freelanzer1.videolistapp2.ui.video_list.components.AlbumItem
import ru.freelanzer1.videolistapp2.ui.video_list.components.OrderSection
import ru.freelanzer1.videolistapp2.ui.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditAlbumScreen.route)
                },
                Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add album")
            }

        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your video with tags",
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(AlbumsEvent.ToggleOrderSection)
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
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    albumOrder = state.albumOrder,
                    onOrderChange = {
                        viewModel.onEvent(AlbumsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.albums) { album ->
                    AlbumItem(
                        album = album,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditAlbumScreen.route +
//                                            "?albumId=${album.id}&albumColor=${album.color}"
                                            "?albumId=${album.id}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(AlbumsEvent.DeleteAlbum(album))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Album deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(AlbumsEvent.RestoreAlbum)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}