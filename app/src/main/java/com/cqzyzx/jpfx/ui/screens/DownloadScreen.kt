package com.cqzyzx.jpfx.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.TopBarTitle
import com.cqzyzx.jpfx.ui.components.ad.RequestExpressAd
import com.cqzyzx.jpfx.ui.components.ad.requestRewardAd
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.viewmodel.DownloadViewModel
import com.cqzyzx.jpfx.viewmodel.SoftViewModel
import kotlinx.coroutines.launch


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
    val coroutineScope = rememberCoroutineScope()
    var adRewardStatus by remember {
        mutableStateOf(false)
    }
    var click by remember {
        mutableStateOf(false)
    }
    val userInfo by remember {
        mutableStateOf(SharedPreferencesUtils(context).getUserInfo())
    }

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
            if(userInfo.adType1>0){
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                ) {
                    RequestExpressAd(context)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()){
                    Column {
                        Text(modifier = Modifier.fillMaxWidth(), text = "下载识别ID：${downloadViewModel.adDetail.adId}", fontSize = 12.sp, textAlign = TextAlign.Center)
                        Text(modifier = Modifier.fillMaxWidth(), text = "应用识别ID：${downloadViewModel.adDetail.id}", fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()){
                    Button(onClick = {
                        if(!click){
                            click = true
                            if(adRewardStatus){
                                navController.navigate(Destinations.WebViewFrame.route+"/${downloadViewModel.adDetail.id}/${downloadViewModel.adDetail.adId}")
                            }else{
                                requestRewardAd(context, onClose = {msg->
                                    if(msg=="onReward"){
                                        // 视频观看完成
                                        coroutineScope.launch {
                                            downloadViewModel.adReward(context,downloadViewModel.adDetail.id,downloadViewModel.adDetail.adId)
                                            adRewardStatus = true
                                            navController.navigate(Destinations.WebViewFrame.route+"/${downloadViewModel.downloadInfo.id}/${downloadViewModel.downloadInfo.adId}")
                                        }
                                    }else if(msg=="loadRewardAdFail"){
                                        // 广告加载失败
                                        coroutineScope.launch {
                                            downloadViewModel.adReward(context,downloadViewModel.adDetail.id,downloadViewModel.adDetail.adId)
                                            navController.navigate(Destinations.WebViewFrame.route+"/${downloadViewModel.downloadInfo.id}/${downloadViewModel.downloadInfo.adId}")
                                        }
                                    }
                                })
                            }
                        }else{
                            CommonUtils.toast(context,"请勿重复点击")
                        }
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