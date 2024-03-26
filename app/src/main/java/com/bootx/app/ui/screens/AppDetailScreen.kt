package com.bootx.app.ui.screens

import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.SoftIcon6
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.components.ad.RequestBannerAd
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.ShareUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.DownloadViewModel
import com.bootx.app.viewmodel.SoftViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun AppDetailScreen(
    navController: NavHostController,
    id: String,
    downloadViewModel: DownloadViewModel = viewModel(),
    softViewModel: SoftViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    var progress by remember {
        mutableIntStateOf(0)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        softViewModel.detail(context, SharedPreferencesUtils(context).get("token"), id)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = softViewModel.softDetail.name) },
            navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            },
        )
    }, bottomBar = {
        BottomAppBar {
            TextButton(onClick = { /*TODO*/ }) {
                Column(modifier = Modifier.clickable {
                    coroutineScope.launch {

                    }
                }) {
                    Icon(imageVector = Icons.Filled.CurrencyBitcoin, contentDescription = "")
                    Text(text = "投币")
                }
            }
            Button(modifier = Modifier.weight(1.0f), onClick = {
                val token = SharedPreferencesUtils(context).get("token")
                if(token.isBlank()){
                    showDialog = true
                }else{
                    navController.navigate(Destinations.WebViewFrame.route + "/${softViewModel.softDetail.id}/0")
                }


            }) {
                Text(text = "下载")
            }
            TextButton(onClick = {
                val shareAppList = ShareUtils.getShareAppList(context)
                Log.e("shareAppList", "AppDetailScreen: ${shareAppList.toString()}")
            }) {
                Column(modifier = Modifier.clickable {
                    ShareUtils.shareText(context, "abc")
                }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                    Text(text = "分享")
                }
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
                    ListItem(headlineContent = {
                        Text(
                            text = softViewModel.softDetail.name,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.primary,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, supportingContent = {
                        Text(
                            text = softViewModel.softDetail.fullName ?: "",
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.secondary,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, leadingContent = {
                        SoftIcon6(url = softViewModel.softDetail.logo)
                    })
                }
                item {
                    RequestBannerAd(context = context)
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
                            DetailItem(softViewModel.softDetail.downloads, "下载")
                        }
                    }
                }
                if (softViewModel.softDetail.memo != null && softViewModel.softDetail.memo != "") {
                    item {
                        Text(
                            text = "更新内容",
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
            text = title, fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = title2,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}