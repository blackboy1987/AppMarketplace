package com.bootx.app.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.ui.screens.AppDetailScreen
import com.bootx.app.ui.screens.MainScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavHostApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Destinations.MainFrame.route+"/0",
    ) {
        composable(
            Destinations.MainFrame.route + "/{type}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
        ) {
            val type = it.arguments?.getString("type") ?: "0"
            MainScreen(navController, type)
        }

        composable(
            Destinations.AppDetailFrame.route + "/{id}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
        ) {
            val id = it.arguments?.getString("id") ?: ""
            AppDetailScreen(navController, id)
        }
    }
}