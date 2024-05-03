package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.extension.onBottomReached
import com.bootx.app.extension.onScroll
import com.bootx.app.ui.components.SoftIcon4
import com.bootx.app.ui.components.SoftItem1
import com.bootx.app.ui.components.ad.requestInteractionAd
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.ui.theme.fontSize12
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.SoftViewModel
import com.bootx.yysc.ui.components.SwiperItem
import com.bootx.app.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.util.Date

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
    var showDialog by remember {
        mutableStateOf<Boolean>(false)
    }
    LaunchedEffect(Unit) {
        homeViewModel.category(context)
        homeViewModel.load(context)
        if(homeViewModel.homeData.notice.size>0 && SharedPreferencesUtils(context).get("homeNoticeShowDialog_"+CommonUtils.formatDate(Date(),"yyyy-MM-dd")+(homeViewModel.homeData.notice[0].id)).isEmpty()){
            showDialog = true
        }
        softViewModel.list(context, 1, categoryId)
        requestInteractionAd(context){

        }
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
                SoftIcon4(url = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png")
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

            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val pagerState = rememberPagerState(pageCount = {
                homeViewModel.categoryList.size
            },initialPage= selectedTabIndex+1)

            Column {
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
                        SecondaryScrollableTabRow(
                            selectedTabIndex = selectedTabIndex,
                            edgePadding = 0.dp,
                            divider = {},
                            // 自定义指示器
                            indicator = { tabPositions ->
                               val tabPosition = tabPositions[selectedTabIndex]
                                val currentTabWidth by animateDpAsState(
                                    targetValue = tabPosition.contentWidth/2,
                                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                                    label = ""
                                )
                                val indicatorOffset by animateDpAsState(
                                    targetValue = tabPosition.left,
                                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                                    label = ""
                                )
                                Box(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .wrapContentSize(Alignment.BottomStart)
                                       .offset(x = indicatorOffset + tabPosition.width / 2 - currentTabWidth / 2)
                                       .clip(RoundedCornerShape(120.dp))
                                       .width(currentTabWidth)
                                       .height(4.dp)
                                       .background(Color(0xff1b89eb))
                                )
                            }
                        ) {
                            homeViewModel.categoryList.forEachIndexed { index, item ->
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
                                    selectedContentColor=Color(0xff000000),
                                    unselectedContentColor = Color(0xff9ca0ab),
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        selectedTabIndex = index
                                        categoryId = item.id
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
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
                    item{
                        // 监听HorizontalPager切换
                        LaunchedEffect(pagerState) {
                            snapshotFlow { pagerState.currentPage }.collect { page ->
                                selectedTabIndex = page
                                categoryId = homeViewModel.categoryList[selectedTabIndex].id
                                coroutineScope.launch {
                                    if (isStickyHeader) {
                                        lazyListState.animateScrollToItem(1)
                                    }
                                    softViewModel.list(context, 1, categoryId)
                                }
                            }
                        }
                        HorizontalPager(
                            state = pagerState,
                            verticalAlignment = Alignment.Top,
                        ) { page ->
                            Box(modifier = Modifier.height(screenHeight-190.dp)){
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    itemsIndexed(softViewModel.softList) { index, item ->
                                        SoftItem1(item = item, onClick = { id->
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
            }

            if (showDialog) {
                BasicAlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    }, modifier = Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "${homeViewModel.homeData.notice[0].title}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${homeViewModel.homeData.notice[0].title}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Left,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Card(
                                modifier = Modifier.width(120.dp).clickable {
                                    showDialog = false
                                    SharedPreferencesUtils(context).set("homeNoticeShowDialog_"+CommonUtils.formatDate(Date(),"yyyy-MM-dd")+(homeViewModel.homeData.notice[0].id),"0")
                                },
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = Color(0xff98a4af),
                                    contentColor = Color.White,
                                ),
                                shape = RoundedCornerShape(20.dp),
                            ) {
                                Text(text = "今天不显示", modifier = Modifier.padding(4.dp).fillMaxWidth(),
                                    fontSize= fontSize12, textAlign = TextAlign.Center)
                            }
                            Card(
                                modifier = Modifier.width(120.dp).clickable {
                                    showDialog = false
                                },
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White,
                                ),
                                shape = RoundedCornerShape(20.dp),
                            ) {
                                Text(text = "关闭", modifier = Modifier.padding(4.dp).fillMaxWidth(),
                                    fontSize= fontSize12, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    }
}