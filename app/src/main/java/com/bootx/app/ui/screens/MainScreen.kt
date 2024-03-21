package com.bootx.app.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
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
import com.bootx.app.ui.navigation.Destinations

data class NavigationItem(
    val title: String, //底部导航栏的标题
    val icon: ImageVector//底部导航栏图标
)

@Composable
fun MainScreen(navController: NavHostController,type: String="0") {
    val context = LocalContext.current
    val navigationItems = listOf(
        NavigationItem(title = "首页", icon = Icons.Filled.Home),
        NavigationItem(title = "应用分类", icon = Icons.Filled.Apps),
        NavigationItem(title = "个人中心", icon = Icons.Filled.Person),
    )

    var currentNavigationIndex by remember {
        mutableIntStateOf(type.toInt())
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.navigationBarsPadding()
            ){
                navigationItems.forEachIndexed { index, navigationItem ->
                    BottomNavigationItem(
                        selected = currentNavigationIndex == index,
                        onClick = {
                            if(currentNavigationIndex!=index){
                                currentNavigationIndex = index
                                navController.navigate(Destinations.MainFrame.route+"/${index}")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navigationItem.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navigationItem.title)
                        },
                        selectedContentColor = Color(0xFF149EE7),
                        unselectedContentColor = Color(0xFF999999)
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
            when(currentNavigationIndex){
                0-> {
                    HomeScreen(navController = navController)
                }
                1-> {
                    AppScreen(navController = navController)
                }
                2-> {
                    MineScreen(navController = navController)
                }
            }
        }
    }
}


