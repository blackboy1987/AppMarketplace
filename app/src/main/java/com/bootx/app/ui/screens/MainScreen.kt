package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

data class NavigationItem(
    val title: String, //底部导航栏的标题
    val icon: ImageVector//底部导航栏图标
)

@Composable
fun MainScreen(navController: NavHostController, type: String = "0") {
    val context = LocalContext.current
    val navigationItems = listOf(
        NavigationItem(title = "首页", icon = Icons.Filled.Home),
        NavigationItem(title = "应用分类", icon = Icons.Filled.Apps),
        NavigationItem(title = "个人中心", icon = Icons.Filled.Person),
    )

    var selectedItem  by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = Color.Red
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = (selectedItem  == index),
                        onClick = {
                            selectedItem  = index
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        alwaysShowLabel = false,
                        label = {
                            Text(text = item.title)
                        },
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight(),
        ) {
            when (selectedItem) {
                0 -> {
                    HomeScreen(navController = navController)
                }

                1 -> {
                    AppScreen(navController = navController)
                }

                2 -> {
                    MineScreen(navController = navController)
                }
            }
        }
    }
}


