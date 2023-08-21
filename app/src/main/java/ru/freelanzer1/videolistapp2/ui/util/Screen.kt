package ru.freelanzer1.videolistapp2.ui.util

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object AddEditAlbumScreen: Screen("add_edit_album_screen")
}
