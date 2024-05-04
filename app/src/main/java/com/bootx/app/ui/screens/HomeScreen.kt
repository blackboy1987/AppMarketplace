package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.extension.onBottomReached
import com.bootx.app.extension.onScroll
import com.bootx.app.ui.components.Item1
import com.bootx.app.ui.components.PagerTabIndicator1
import com.bootx.app.ui.components.SoftIcon4
import com.bootx.app.ui.components.ad.requestInteractionAd
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.HomeViewModel
import com.bootx.app.viewmodel.SoftViewModel
import com.bootx.yysc.ui.components.SwiperItem
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@SuppressLint("InvalidColorHexValue", "RememberReturnType")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    softViewModel: SoftViewModel = viewModel(),
) {
    val context = LocalContext.current
    CommonUtils.ShowStatus((context as Activity).window)
    val coroutineScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var categoryId by remember { mutableIntStateOf(0) }
    var reachBottom by remember { mutableStateOf(false) }
    var isStickyHeader by remember {
        mutableStateOf(false)
    }
    var loading by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf<Boolean>(false)
    }

    suspend fun loadData(){
        if(!loading){
            loading = true
            softViewModel.switchTab(context, categoryId)
            loading = false
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.load(context)
        if(homeViewModel.homeData.notice.size>0 && SharedPreferencesUtils(context).get("homeNoticeShowDialog_"+ CommonUtils.formatDate(
                Date(),"yyyy-MM-dd")+(homeViewModel.homeData.notice[0].id)).isEmpty()){
            showDialog = true
        }
        //loadData()
        /*requestInteractionAd(context){

        }*/
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
                                .fillMaxWidth()
                                .padding(0.dp),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)){
                        Text(text = "搜索", fontSize = 12.sp)
                    }
                    Box(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)){
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                }
            }, navigationIcon = {
                SoftIcon4(url = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png", modifier = Modifier.clickable {
                    navController.navigate(Destinations.LoginFrame.route)
                })
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
            lazyListState.onScroll { index ->
                isStickyHeader = index >= 1
            }
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }
            val pagerState = rememberPagerState(pageCount = {
                homeViewModel.homeData.categories.size
            }, initialPage = 0)
            val coroutineScope = rememberCoroutineScope()
            // 监听HorizontalPager切换
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    selectedTabIndex = page
                    reachBottom = false
                    if(!homeViewModel.homeData.categories.isEmpty()){
                        categoryId = homeViewModel.homeData.categories[page].id;
                    }else{
                        categoryId = 0
                    }
                    // 加载数据
                    coroutineScope.launch {
                        loadData()
                    }
                }
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                item {
                    SwiperItem(homeViewModel.homeData.carousel){url->
                        navController.navigate(Destinations.AppDetailFrame.route+"/"+url)
                    }
                }
                stickyHeader {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxWidth(),
                        edgePadding = 0.dp,
                        divider = {},
                        indicator = { tabPositions ->
                            if (tabPositions.isNotEmpty()) {
                                PagerTabIndicator1(
                                    tabPositions = tabPositions,
                                    pagerState = pagerState
                                )
                            }
                        },
                    ) {
                        homeViewModel.homeData.categories.forEachIndexed { index, item ->
                            Tab(
                                text = {
                                    if (index == selectedTabIndex) {
                                        Text(
                                            item.name,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    } else {
                                        Text(item.name)
                                    }
                                },
                                selectedContentColor = Color(0xff000000),
                                unselectedContentColor = Color(0xff9ca0ab),
                                selected = selectedTabIndex == index,
                                onClick = {
                                    reachBottom = false
                                    selectedTabIndex = index
                                    coroutineScope.launch {
                                        if (isStickyHeader) {
                                            lazyListState.animateScrollToItem(1)
                                        }
                                        pagerState.animateScrollToPage(index)
                                        loadData()
                                    }
                                },
                            )
                        }
                    }
                }
                item{
                    val lazyListState1 = rememberLazyListState()
                    lazyListState1.onBottomReached(buffer = 3) {
                        reachBottom = true
                        // 注意 页面刚加载的时候，这里也会执行，所以需要判断。如果是第一页这里不调用
                        if(!loading){
                            loading = true
                            // 滑动到底部加载下一页面
                            coroutineScope.launch {
                                softViewModel.list(context,  categoryId)
                                loading = false
                            }
                        }
                    }
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp.dp
                    HorizontalPager(
                        state = pagerState,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .height(screenHeight)
                                .verticalScroll(state = rememberScrollState())
                        ){
                            LazyColumn(
                                state = lazyListState1,
                                modifier = Modifier.height(screenHeight)
                            ) {
                                item{
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                if(selectedTabIndex == page){
                                    itemsIndexed(softViewModel.softList) { index, item ->
                                        key(item.id){
                                            Item1(item){id->
                                                navController.navigate(Destinations.AppDetailFrame.route+"/$id")
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}