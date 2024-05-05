package com.cqzyzx.jpfx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.ui.theme.selectColor
import com.cqzyzx.jpfx.ui.theme.selectIconColor
import com.cqzyzx.jpfx.ui.theme.unSelectColor
import com.cqzyzx.jpfx.ui.theme.unSelectTextColor
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
)

@Composable
fun MainScreen(navController: NavHostController, type: String = "0") {
    val context = LocalContext.current
    val navigationItems = listOf(
        NavigationItem(title = "首页", icon = Icons.Filled.Home),
        NavigationItem(title = "应用分类", icon = Icons.Filled.Category),
        NavigationItem(title = "个人中心", icon = Icons.Filled.Person),
    )

    var selectedItem by remember {
        try {
            mutableIntStateOf(SharedPreferencesUtils(context).get("homeIndex").toInt())
        } catch (e: Exception) {
            mutableIntStateOf(1)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
            ) {
                navigationItems.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = (selectedItem == index),
                        onClick = {
                            selectedItem = index
                        },
                        icon = {
                            if (selectedItem == index) {
                                Card(
                                    colors = CardDefaults.cardColors().copy(
                                        containerColor = selectColor,
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = selectIconColor,
                                    )
                                }
                            } else {
                                Card(
                                    colors = CardDefaults.cardColors().copy(
                                        containerColor = Color.White,
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = unSelectColor,
                                    )
                                }
                            }
                        },
                        label = {
                            if(selectedItem==index){
                                Text(text = item.title,color=Color.Black, fontWeight = FontWeight.Bold)
                            }else{
                                Text(text = item.title,color= unSelectTextColor)
                            }
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .background(Color.Red),
        ) {
            when (selectedItem) {
                0 -> {
                    SharedPreferencesUtils(context).set("homeIndex","0")
                    HomeScreen(navController = navController)
                }
                1 -> {
                    SharedPreferencesUtils(context).set("homeIndex","0")
                    AppScreen(navController = navController)
                }
                2 -> {
                    // 判断是否登录
                    val token = SharedPreferencesUtils(context).get("token")
                    if(token.isNotBlank()){
                        SharedPreferencesUtils(context).set("homeIndex","0")
                        MineScreen(navController = navController)
                    }else{
                        navController.navigate(Destinations.LoginFrame.route)
                    }

                }
            }
        }
    }
}


