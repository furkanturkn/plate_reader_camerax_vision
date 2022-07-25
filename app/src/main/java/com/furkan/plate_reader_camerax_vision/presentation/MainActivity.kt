package com.furkan.plate_reader_camerax_vision.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.furkan.plate_reader_camerax_vision.BuildConfig
import com.furkan.plate_reader_camerax_vision.presentation.camera.CameraScreen
import com.furkan.plate_reader_camerax_vision.presentation.scan.ScanScreen
import com.furkan.plate_reader_camerax_vision.presentation.ui.theme.Plate_reader_camerax_visionTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "camera_screen"
            ) {

                composable("camera_screen") {
                    CameraScreen(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(color = Color.Black),
                        onNavigate = navController::navigate
                    )
                }

                composable(
                    route = "convert_screen/{strUri}",
                    arguments = listOf(
                        navArgument(name = "strUri") {
                            type = NavType.StringType
                            nullable = true
                        }
                    )) {
                    val strUri = it.arguments?.getString("strUri")!!
                    ScanScreen(
                        strUri = strUri, modifier = Modifier
                            .fillMaxHeight()
                            .background(color = Color.Black)
                    )
                }

            }
        }
    }
}
