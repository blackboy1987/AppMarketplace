package com.cqzyzx.jpfx.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.MyWebView
import com.cqzyzx.jpfx.ui.components.TopBarTitle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageScreen(
    navController: NavHostController,
    type: String,
    title: String,
) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon(
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }, title = { TopBarTitle(text = title) })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            MyWebView(url = Config.baseUrl+"/page/"+type)
        }

    }
}