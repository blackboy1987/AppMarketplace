package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.MyWebView
import com.bootx.app.ui.components.TopBarTitle


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun WebViewScreen(
    navController: NavHostController,
    id: String,
) {
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
        Box(modifier = Modifier.padding(it)){
            MyWebView(url = "https://www.baidu.com")
        }
    }
}