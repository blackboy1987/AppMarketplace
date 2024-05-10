package com.cqzyzx.jpfx.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.R
import com.cqzyzx.jpfx.ui.theme.selectColor
import com.cqzyzx.jpfx.ui.theme.selectIconColor
import com.cqzyzx.jpfx.ui.theme.unSelectColor
import com.cqzyzx.jpfx.ui.theme.unSelectTextColor
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val icon: Painter,
    val selectIcon: Painter,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController, type: String = "0") {
    val context = LocalContext.current
    val navigationItems = listOf(
        NavigationItem(
            title = "首页",
            icon = painterResource(id = R.drawable.home0),
            selectIcon = painterResource(id = R.drawable.home1)
        ),
        NavigationItem(
            title = "应用分类",
            icon = painterResource(id = R.drawable.category0),
            selectIcon = painterResource(id = R.drawable.category1)
        ),
        NavigationItem(
            title = "个人中心",
            icon = painterResource(id = R.drawable.mine0),
            selectIcon = painterResource(id = R.drawable.mine1)
        ),
    )

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    var cornerShape = rememberCoroutineScope()
    var pagerState = rememberPagerState(
        initialPage=selectedItem,
        pageCount = {navigationItems.size}
    )

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
                            cornerShape.launch {
                                Log.e("cornerShape", "MainScreen: $index", )
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            BarIcon(item,selectedItem == index)
                        },
                        label = {
                           BarLabel(item,selectedItem == index)
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
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) {index->
                when(index){
                    0 -> {
                        HomeScreen(navController)
                    }
                    1 -> {
                        AppScreen(navController)
                    }
                    2 -> {
                        MineScreen(navController = navController)
                    }

                }
            }
        }
    }
}

@Composable
fun BarLabel(item: NavigationItem, selected: Boolean) {
    if (selected) {
        Text(
            text = item.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    } else {
        Text(text = item.title, color = unSelectTextColor)
    }
}

@Composable
fun BarIcon(item:NavigationItem,selected: Boolean) {
    if (selected) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = selectColor,
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 4.dp
                    )
                    .size(20.dp),
                painter = item.selectIcon,
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
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 4.dp
                    )
                    .size(20.dp),
                painter = item.icon,
                contentDescription = null,
                tint = unSelectColor,
            )
        }
    }
}


