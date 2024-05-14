package com.cqzyzx.jpfx.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cqzyzx.jpfx.entity.AdConfig
import com.cqzyzx.jpfx.entity.HomeEntity
import com.cqzyzx.jpfx.entity.SiteConfig
import com.cqzyzx.jpfx.ui.components.Item1
import com.cqzyzx.jpfx.ui.components.Loading1
import com.cqzyzx.jpfx.ui.components.NotificationBar
import com.cqzyzx.jpfx.ui.components.ad.requestInteractionAd
import com.cqzyzx.jpfx.ui.components.home.SwiperItem
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.ui.theme.fontSize12
import com.cqzyzx.jpfx.ui.theme.selectColor
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.viewmodel.HomeViewModel
import com.google.gson.Gson
import java.util.Date
import java.util.UUID

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@SuppressLint("InvalidColorHexValue", "RememberReturnType")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
) {
    var status by rememberSaveable {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    CommonUtils.ShowStatus((context as Activity).window)
    var showDialog by remember {
        mutableStateOf<Boolean>(false)
    }
    val userInfo by remember {
        mutableStateOf(SharedPreferencesUtils(context).getUserInfo())
    }
    var loading by remember {
        mutableStateOf(false)
    }
    val selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var key = rememberSaveable {
        UUID.randomUUID().toString()
    }
    val categories = listOf("最近更新")
    var homeData by remember {
        mutableStateOf(HomeEntity())
    }
    LaunchedEffect(status) {
        if(status!=1){
            homeViewModel.load(context)
            status = homeViewModel.status
            homeData = homeViewModel.homeData
            // 通知公告是否需要弹出
            if (homeViewModel.homeData.notice1.isNotEmpty() && SharedPreferencesUtils(context).get(
                    "homeNoticeShowDialog_" + CommonUtils.formatDate(
                        Date(), "yyyy-MM-dd"
                    ) + (homeViewModel.homeData.notice1[0].id)
                ).isEmpty()
            ) {
                showDialog = true
            }
            loadRequestInteractionAd(context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Card(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(Destinations.SearchFrame.route)
                                    }
                                    .fillMaxSize(),
                                colors = CardDefaults.cardColors().copy(
                                    containerColor = Color.White,
                                ),
                                border = BorderStroke(1.dp, Color(0xffd5d5d5))
                            ) {

                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp)
                        ) {
                            Text(text = "搜索", fontSize = 14.sp, color = Color(0xff737373))
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "",
                                tint = Color(0xff737373)
                            )
                        }
                    }
                }, navigationIcon = {
                    Card(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp),
                        shape = CircleShape,
                    ) {
                        if (userInfo.avatar.isNotBlank()) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp),
                                contentScale = ContentScale.Crop,
                                model = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png",
                                contentDescription = ""
                            )
                        } else {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        navController.navigate(Destinations.LoginFrame.route)
                                    }
                                    .size(40.dp),
                                contentScale = ContentScale.Crop,
                                model = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png",
                                contentDescription = ""
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Destinations.HistoryFrame.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp),
                            tint = Color(0xff737373)
                        )
                    }
                })
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight(),
            color = Color.White,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 0.dp)
            ){
                if(homeViewModel.homeData.carousel.size>0){
                    item {
                        SwiperItem(homeViewModel.homeData.carousel) { url ->
                            if(url.startsWith("http")){
                                navController.navigate(Destinations.WebView2Frame.route + "/" + url)
                            }else{
                                navController.navigate(Destinations.AppDetailFrame.route + "/" + url)
                            }

                        }
                    }
                }
                if(!homeViewModel.homeData.notice0.isEmpty()){
                    item{
                        NotificationBar("${homeViewModel.homeData.notice0[0].content}")
                    }
                }
                stickyHeader {
                    ScrollableTabRow(
                        selectedTabIndex = 0,
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(Color.White)
                            .fillMaxWidth(),
                        edgePadding = 0.dp,
                        divider = {},
                        indicator = { tabPositions ->

                        },
                    ) {
                        categories.forEachIndexed { index, item ->
                            Tab(
                                text = {
                                    if (index == selectedTabIndex) {
                                        Text(
                                            item,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    } else {
                                        Text(item)
                                    }
                                },
                                selectedContentColor = Color(0xff000000),
                                unselectedContentColor = Color(0xff9ca0ab),
                                selected = selectedTabIndex == index,
                                onClick = {

                                },
                            )
                        }
                    }
                }
                if(loading){
                    item{
                        Loading1()
                    }
                }
                itemsIndexed(homeViewModel.homeData.list) { index, item ->
                    key(item.id) {
                        Item1(item) { id ->
                            navController.navigate(Destinations.AppDetailFrame.route + "/$id")
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
                        text = "${homeViewModel.homeData.notice1[0].title}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${homeViewModel.homeData.notice1[0].content}",
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
                            modifier = Modifier
                                .width(120.dp)
                                .clickable {
                                    showDialog = false
                                    SharedPreferencesUtils(context).set(
                                        "homeNoticeShowDialog_" + CommonUtils.formatDate(
                                            Date(),
                                            "yyyy-MM-dd"
                                        ) + (homeViewModel.homeData.notice1[0].id), "0"
                                    )
                                },
                            colors = CardDefaults.cardColors().copy(
                                containerColor = Color(0xff98a4af),
                                contentColor = Color.White,
                            ),
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Text(
                                text = "今天不显示", modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                                fontSize = fontSize12, textAlign = TextAlign.Center
                            )
                        }
                        Card(
                            modifier = Modifier
                                .width(120.dp)
                                .clickable {
                                    showDialog = false
                                },
                            colors = CardDefaults.cardColors().copy(
                                containerColor = selectColor,
                                contentColor = Color.White,
                            ),
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Text(
                                text = "关闭", modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                                fontSize = fontSize12, textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * 加载广告
 */
fun loadRequestInteractionAd(context:Context){
    // 缓存key
    val cacheKey = "requestInteractionAdCount_"+CommonUtils.formatDate(Date(),"yyyyMMdd")
    val gson = Gson()
    var adCount = 0
    var readAdCount = 0
    val get = SharedPreferencesUtils(context).get("settingConfig")
    adCount = try {
        val siteConfig = gson.fromJson(get, SiteConfig::class.java)
        if(SharedPreferencesUtils(context).get("token").isEmpty()){
            // 未登录
            siteConfig.adType4Count
        }else{
            siteConfig.memberAdType4Count
        }

    }catch (e: Exception){
        0
    }

    readAdCount = try {
        val get1 = SharedPreferencesUtils(context).get(cacheKey)
        get1.toInt()
    }catch (e: Exception){
        0
    }
    // 获取用户信息
    if (
        // 加载成功
        SharedPreferencesUtils(context).get("adType6") != "0"
        // 用户点击关闭
        && SharedPreferencesUtils(context).get("adType6") != "3"
        // 还需要看广告
        && adCount>readAdCount) {
        requestInteractionAd(context) { status ->
            if(status=="3"){
                try {
                    SharedPreferencesUtils(context).set(cacheKey,(readAdCount+1).toString())
                }catch (e:Exception){
                    SharedPreferencesUtils(context).set(cacheKey,"1")
                }
            }


            SharedPreferencesUtils(context).set("adType6", status)
        }
    }
}

@Composable fun HomeTopBar(){

}