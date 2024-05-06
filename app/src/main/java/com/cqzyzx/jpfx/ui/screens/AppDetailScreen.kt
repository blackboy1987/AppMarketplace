package com.cqzyzx.jpfx.ui.screens

import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.SoftIcon6
import com.cqzyzx.jpfx.ui.components.TopBarTitle
import com.cqzyzx.jpfx.ui.components.ad.RequestExpressAd
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.ShareUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.viewmodel.CollectLogViewModel
import com.cqzyzx.jpfx.viewmodel.SoftViewModel
import kotlinx.coroutines.launch

@Composable
fun AppDetailScreen(
    navController: NavHostController,
    id: String,
    softViewModel: SoftViewModel = viewModel(),
    collectLogViewModel: CollectLogViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var loading by remember {
        mutableStateOf<Boolean>(false)
    }
    val userInfo by remember {
        mutableStateOf(SharedPreferencesUtils(context).getUserInfo())
    }
    LaunchedEffect(Unit) {
        softViewModel.detail(context, SharedPreferencesUtils(context).get("token"), id)
    }

    Scaffold(
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        topBar = {
        TopAppBar(
            backgroundColor = Color.White,
            elevation = 0.dp,
            title = { TopBarTitle(text = softViewModel.softDetail.name) },
            navigationIcon = {
                LeftIcon {
                    if(!loading){
                        loading = true
                        navController.popBackStack()
                    }
                }
            },
        )
    }, bottomBar = {
        BottomAppBar(
            containerColor = Color.White,
        ) {
            IconButton(modifier = Modifier.padding(8.dp),onClick = {
                ShareUtils.shareText(context, "abc")
            }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = "")
            }
            Button(modifier = Modifier.weight(1.0f), onClick = {
                val token = SharedPreferencesUtils(context).get("token")
                // 需要检测是否有下载地址
                coroutineScope.launch {
                    val flag = softViewModel.checkDownload(context,id)
                    if(flag){
                        if(token.isBlank()){
                            showDialog = true
                        }else{
                            navController.navigate(Destinations.WebViewFrame.route + "/${softViewModel.softDetail.id}/0")
                        }
                    }else{
                        CommonUtils.toast(context,"暂无下载地址")
                    }
                }
            }) {
                Text(text = "下载")
            }
            IconButton(modifier = Modifier.padding(8.dp),onClick = {
                coroutineScope.launch {
                    collectLogViewModel.add(context,softViewModel.softDetail.id)
                }
            }) {
                Icon(imageVector = Icons.Filled.Collections, contentDescription = "")
            }
        }
    }) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                item {
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = Color.White
                        ),
                        headlineContent = {
                        Text(
                            text = softViewModel.softDetail.name,
                            maxLines = 1,
                            color = Color(0xff111111),
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, supportingContent = {
                        Text(
                            text = softViewModel.softDetail.fullName ?: "",
                            maxLines = 1,
                            color = Color(0xffaaaaaa),
                            overflow = TextOverflow.Ellipsis
                        )
                    }, leadingContent = {
                        SoftIcon6(url = softViewModel.softDetail.logo)
                    })
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailItem(
                                softViewModel.softDetail.score,
                                "评分"
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailItem(softViewModel.softDetail.size, "大小")
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailItem("${softViewModel.softDetail.reviewCount}", "阅读")
                        }
                    }
                }
                if(userInfo.adType1>0){
                    item {
                        RequestExpressAd(context)
                    }
                }

                if (softViewModel.softDetail.memo != null && softViewModel.softDetail.memo != "") {
                    item {
                        Text(
                            text = "应用介绍",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        AndroidView(factory = { context ->
                            TextView(context).apply {
                                text = Html.fromHtml(softViewModel.softDetail.memo)
                            }
                        })
                    }
                }
            }
        }

        if (showDialog) {
            androidx.compose.material3.AlertDialog(
                containerColor = Color.White,
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        navController.navigate(Destinations.LoginFrame.route)
                    }) {
                        Text(text = "去登录")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        navController.navigate(Destinations.DownloadFrame.route + "/${softViewModel.softDetail.id}")
                    }) {
                        Text(text = "跳转广告页面")
                    }
                },
                title = {
                    Text(text = "您还未登录")
                },
                text = {
                    Text(text = "您可以选择登录后免广告下载，或者免登录观看广告后下载")
                })
        }

    }
}

@Composable
fun DetailItem(title: String, title2: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title, fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = Color(0xff111111),
        )
        Text(
            text = title2,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = Color(0xffaaaaaa),
        )
    }
}