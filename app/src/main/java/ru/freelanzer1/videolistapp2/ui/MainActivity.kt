package ru.freelanzer1.videolistapp2.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.freelanzer1.videolistapp2.ui.add_edit_album.AddEditAlbumScreen
import ru.freelanzer1.videolistapp2.ui.video_list.MainScreen
import ru.freelanzer1.videolistapp2.ui.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import ru.freelanzer1.videolistapp2.ui.theme.ExtendedTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ExtendedTheme () {
                Surface(
                    //color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditAlbumScreen.route +
                                    //"?albumId={albumId}&noteColor={noteColor}",
                            "?albumId={albumId}",
                            arguments = listOf(
                                navArgument(
                                    name = "albumId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
//                                navArgument(
//                                    name = "noteColor"
//                                ) {
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                },
                            )
                        ) {
//                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditAlbumScreen(
                                navController = navController,
 //                               noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
