package com.cqzyzx.jpfx.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.cqzyzx.jpfx.R

/**
 * 开屏广告加载成功之前显示的界面
 */
@Composable
fun PreSplash (){
    // 居中显示logo
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
    }
}