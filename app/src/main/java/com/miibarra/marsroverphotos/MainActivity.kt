package com.miibarra.marsroverphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.miibarra.marsroverphotos.ui.views.RoverPhotosScreenBySol
import com.miibarra.marsroverphotos.ui.theme.MarsRoverPhotos
import com.miibarra.marsroverphotos.ui.views.PhotoDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarsRoverPhotos {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "rover_photo_list_screen") {
                    composable("rover_photo_list_screen") {
                        RoverPhotosScreenBySol(navController = navController)
                    }
                    composable("photo_detail_screen/{dominantColor}/{id}",
                        arguments = listOf(
                            navArgument("backgroundColor") {
                                type = NavType.IntType
                            },
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val backgroundColor = remember {
                            val color = it.arguments?.getInt("backgroundColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val photoId = remember {
                            it.arguments?.getInt("id")
                        }
                        PhotoDetailScreen(
                            dominantColor = backgroundColor,
                            photoId = photoId ?: 102693,
                            navController = navController)
                    }
                }
            }
        }
    }
}

