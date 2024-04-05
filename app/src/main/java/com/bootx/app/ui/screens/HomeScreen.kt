package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.extension.onBottomReached
import com.bootx.app.extension.onScroll
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.SoftItem
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.viewmodel.SoftViewModel
import com.bootx.yysc.ui.components.SwiperItem
import com.bootx.yysc.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("InvalidColorHexValue")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    softViewModel: SoftViewModel = viewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var categoryId by remember { mutableIntStateOf(0) }
    var isStickyHeader by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        homeViewModel.category(context)
        softViewModel.list(context, 1, categoryId)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clickable {
                        navController.navigate(Destinations.SearchFrame.route)
                    }) {
                    Box(modifier = Modifier.fillMaxSize()){
                        OutlinedTextField(
                            enabled = false,
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .height(32.dp)
                                .padding(0.dp),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)){
                        Text(text = "搜索", fontSize = 12.sp)
                    }
                }
            }, navigationIcon = {
                LeftIcon {

                }
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Apps, contentDescription = "")
                }
            })
        },
    ) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .background(Color.Red),
        ) {

            val lazyListState = rememberLazyListState()
            lazyListState.onBottomReached(buffer = 3) {
                coroutineScope.launch {
                    softViewModel.list(context, softViewModel.pageNumber + 1, categoryId)
                }
            }
            lazyListState.onScroll { index ->
                isStickyHeader = index >= 1
            }
            Column {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                ) {
                    item {
                        SwiperItem()
                    }
                    stickyHeader {
                        SecondaryScrollableTabRow(
                            selectedTabIndex = selectedTabIndex,
                            edgePadding = 0.dp,
                            divider = {},
                            // 自定义指示器
                            indicator = { tabPositions ->
                                val tabPosition = tabPositions[selectedTabIndex]
                                SecondaryIndicator(
                                    height = 2.dp,
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPosition)
                                        .width(10.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        ) {
                            homeViewModel.categoryList.forEachIndexed { index, item ->
                                Tab(
                                    text = {
                                        if (index == selectedTabIndex) {
                                            Text(
                                                item.name,
                                                fontWeight = FontWeight.Bold
                                            )
                                        } else {
                                            Text(item.name)
                                        }
                                    },
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        selectedTabIndex = index
                                        categoryId = item.id
                                        coroutineScope.launch {
                                            if (isStickyHeader) {
                                                lazyListState.animateScrollToItem(1)
                                            }
                                            softViewModel.list(context, 1, categoryId)
                                        }
                                    },
                                )
                            }
                        }
                    }
                    itemsIndexed(softViewModel.softList) { index, item ->
                        SoftItem(item = item, onClick = { id->
                            coroutineScope.launch {
                                navController.navigate(Destinations.AppDetailFrame.route + "/${item.id}")
                            }
                        })
                    }
                }
            }


        }
    }
}