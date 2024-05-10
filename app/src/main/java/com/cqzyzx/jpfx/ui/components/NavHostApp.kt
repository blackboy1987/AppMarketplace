package com.cqzyzx.jpfx.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.ui.screens.AboutScreen
import com.cqzyzx.jpfx.ui.screens.AnimatedScreen
import com.cqzyzx.jpfx.ui.screens.AppDetailScreen
import com.cqzyzx.jpfx.ui.screens.CollectLogScreen
import com.cqzyzx.jpfx.ui.screens.DownloadScreen
import com.cqzyzx.jpfx.ui.screens.HistoryScreen
import com.cqzyzx.jpfx.ui.screens.LoginScreen
import com.cqzyzx.jpfx.ui.screens.MainScreen
import com.cqzyzx.jpfx.ui.screens.PageScreen
import com.cqzyzx.jpfx.ui.screens.RegisterScreen
import com.cqzyzx.jpfx.ui.screens.SearchScreen
import com.cqzyzx.jpfx.ui.screens.SettingScreen
import com.cqzyzx.jpfx.ui.screens.TouGaoAppInfoListScreen
import com.cqzyzx.jpfx.ui.screens.TouGaoScreen
import com.cqzyzx.jpfx.ui.screens.WebViewScreen
import com.cqzyzx.jpfx.ui.screens.WebViewScreen2

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavHostApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Destinations.MainFrame.route+"/1",
        // startDestination = Destinations.SearchFrame.route,
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
            Destinations.WebView2Frame.route + "/{url}",
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
            val url = it.arguments?.getString("url") ?: ""
            WebViewScreen2(navController, url)
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
        composable(
            Destinations.AnimatedFrame.route,
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
            AnimatedScreen(navController)
        }
        composable(
            Destinations.HistoryFrame.route,
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
            HistoryScreen(navController)
        }
        composable(
            Destinations.CollectLogFrame.route,
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
            CollectLogScreen(navController)
        }
    }
}