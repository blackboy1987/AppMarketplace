package com.bootx.app.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.components.ad.RequestBannerAd
import com.bootx.app.ui.components.ad.requestRewardAd
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.CommonUtils
import com.bootx.app.viewmodel.DownloadViewModel
import com.bootx.app.viewmodel.SoftViewModel


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun DownloadScreen(
    navController: NavHostController,
    id: String,
    downloadViewModel: DownloadViewModel = viewModel(),
    softViewModel: SoftViewModel = viewModel(),
) {
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        downloadViewModel.download1(context,id)
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
            ) {
                RequestBannerAd(context = context)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth()){
                    Column {
                        Text(modifier = Modifier.fillMaxWidth(), text = "下载识别ID：${downloadViewModel.adDetail.adId}", fontSize = 12.sp, textAlign = TextAlign.Center)
                        Text(modifier = Modifier.fillMaxWidth(), text = "应用识别ID：${downloadViewModel.adDetail.id}", fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()){
                    Button(onClick = {
                        requestRewardAd(context, onClose = {msg->
                            Log.e("requestRewardAd", "DownloadScreen: $msg")
                            if(msg=="onReward"){
                                CommonUtils.toast(context, msg)
                            }
                        })
                        navController.navigate(Destinations.WebViewFrame.route+"/1")
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)) {
                        Text(text = "观看视频广告后下载")
                    }
                }
            }
        }

    }
}