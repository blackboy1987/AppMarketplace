package com.bootx.app.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.azhon.appupdate.listener.OnDownloadListener
import com.azhon.appupdate.manager.DownloadManager
import com.bootx.app.R
import com.bootx.app.repository.entity.DownloadUrl
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.MyWebView
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.DownloadUtils
import com.bootx.app.util.IDownloadCallback
import com.bootx.app.viewmodel.DownloadViewModel
import java.io.File


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
        mutableDoubleStateOf(0.0)
    }
    var downloadInfo by remember {
        mutableStateOf(DownloadUrl(id = 0, adId = "", url = "", type = 1, name = ""))
    }

    LaunchedEffect(Unit) {
        // 获取具体的下载地址
        downloadInfo = downloadViewModel.getUrl(context, id)
        if (downloadInfo.type == 0) {
            // 可以直接进行下载
            DownloadUtils.downloadFile(
                context,
                downloadInfo.url,
                "abc",
                object : IDownloadCallback {
                    override fun downloading(max: Int, progress: Int) {
                        Log.e("downloading", "downloading: ${max} ${progress}")
                        rate = progress * 1.0 / max
                    }
                })
        }
    }


    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = "免登录下载应用") },
            navigationIcon = {
                LeftIcon {
                    if (downloadInfo.type == 1) {
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
                    Text(modifier = Modifier.clickable {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("${downloadInfo.url}")
                        )
                        context.startActivity(urlIntent)
                    }, text = "浏览器打开")
                }
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            if (downloadInfo.type == 1 && downloadInfo.url.isNotBlank()) {
                MyWebView(url = "${downloadInfo.url}")
            }
        }
    }
}