package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.MyWebView
import com.bootx.app.ui.components.TopBarTitle
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

    LaunchedEffect(Unit) {
        // 获取具体的下载地址
        downloadViewModel.getUrl(context,id)
    }


    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = "免登录下载应用") },
            navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            },
        )
    }) {
        if(!downloadViewModel.downloadInfo.url.isBlank()){
            Box(modifier = Modifier.padding(it)){
                MyWebView(url = "${downloadViewModel.downloadInfo.url}")
            }
        }else{
            Text(text = "error")
        }

    }
}