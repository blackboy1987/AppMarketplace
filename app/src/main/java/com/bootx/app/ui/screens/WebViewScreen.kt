package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.MyWebView
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.DownloadUtils
import com.bootx.app.util.IDownloadCallback
import com.bootx.app.viewmodel.DownloadViewModel


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun WebViewScreen(
    navController: NavHostController,
    id: String,
    adId: String,
    downloadViewModel: DownloadViewModel = viewModel(),
) {

    val context = LocalContext.current
    var rate by remember {
        mutableStateOf("0.00%")
    }

    LaunchedEffect(Unit) {
        // 获取具体的下载地址
        downloadViewModel.getUrl(context, id)
    }


    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = "免登录下载应用") },
            navigationIcon = {
                LeftIcon {
                    if (downloadViewModel.downloadInfo.type == 1) {
                        navController.popBackStack()
                    } else {
                        navController.popBackStack(
                            Destinations.AppDetailFrame.route + "/${id}",
                            false
                        )
                    }

                }
            },
            actions = {
                if (downloadViewModel.downloadInfo.type == 1) {
                    Text(text = "浏览器打开")
                }
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            if (downloadViewModel.downloadInfo.type == 1) {
                if (!downloadViewModel.downloadInfo.url.isBlank()) {
                    MyWebView(url = "${downloadViewModel.downloadInfo.url}")
                } else {
                    Text(text = "error")
                }
            } else if (!downloadViewModel.downloadInfo.url.isBlank()) {
                DownloadUtils.downloadFile(
                    context,
                    downloadViewModel.downloadInfo.url,
                    "abc",
                    object : IDownloadCallback {
                        override fun downloading(max: Int, progress: Int) {
                            rate = "${progress * 100 / max}"
                        }
                    })
                Text(text = rate)
            } else {
                Text(text = "error")
            }
        }
    }
}