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
import com.bootx.app.ui.screens.AboutScreen
import com.bootx.app.ui.screens.AppDetailScreen
import com.bootx.app.ui.screens.DownloadScreen
import com.bootx.app.ui.screens.LoginScreen
import com.bootx.app.ui.screens.MainScreen
import com.bootx.app.ui.screens.PageScreen
import com.bootx.app.ui.screens.WebViewScreen
import com.bootx.app.ui.screens.RegisterScreen
import com.bootx.app.ui.screens.SearchScreen
import com.bootx.app.ui.screens.SettingScreen
import com.bootx.app.ui.screens.TouGaoAppInfoListScreen
import com.bootx.app.ui.screens.TouGaoScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavHostApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Destinations.MainFrame.route+"/0",
        // startDestination = Destinations.AboutFrame.route,
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

        composable(
            Destinations.DownloadFrame.route + "/{id}",
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
            DownloadScreen(navController, id)
        }

        composable(
            Destinations.WebViewFrame.route + "/{id}/{adId}",
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
            val adId = it.arguments?.getString("adId") ?: ""
            WebViewScreen(navController, id,adId)
        }

        composable(
            Destinations.RegisterFrame.route,
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
            RegisterScreen(navController)
        }

        composable(
            Destinations.LoginFrame.route,
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
            LoginScreen(navController)
        }
        composable(
            Destinations.SearchFrame.route,
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
            SearchScreen(navController)
        }
        composable(
            Destinations.AboutFrame.route,
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
            AboutScreen(navController)
        }
        composable(
            Destinations.TouGaoAppInfoListFrame.route,
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
            TouGaoAppInfoListScreen(navController)
        }
        composable(
            Destinations.TouGaoFrame.route + "/{packageName}",
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
            val packageName = it.arguments?.getString("packageName") ?: ""
            TouGaoScreen(navController, packageName)
        }
        composable(
            Destinations.SettingFrame.route,
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
            SettingScreen(navController)
        }
        composable(
            Destinations.PageFrame.route+"/{type}/{title}",
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
            val type = it.arguments?.getString("type") ?: ""
            val title = it.arguments?.getString("title") ?: ""
            PageScreen(navController,type,title)
        }
    }
}