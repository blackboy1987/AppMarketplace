package com.bootx.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.components.ad.RequestSplashAd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdScreen(
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
       /* requestFullScreenVideoAd(context, onClose = {
            Log.e("requestFullScreenVideoAd", "AdScreen: ${it}", )
        })
        requestInteractionAd(context,onClose = {
            Log.e("requestInteractionAd", "AdScreen: ${it}", )
        })*/
    }
    Surface(modifier = Modifier.fillMaxSize()) {
       RequestSplashAd(context){

       }
    }
    /*Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon(
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }, title = { TopBarTitle(text = "关于") })
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Text(text = "横幅广告")
            //RequestBannerAd(context)
            Text(text = "模板广告")
            //RequestExpressAd(context)
            Text(text = "模板视频信息流")
           // RequestExpressDrawFeedAd(context)
            RequestSplashAd(context)
        }

    }*/

}
