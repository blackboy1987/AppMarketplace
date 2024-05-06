package com.cqzyzx.jpfx.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.MyWebView
import com.cqzyzx.jpfx.ui.components.TopBarTitle


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun WebViewScreen2(
    navController: NavHostController,
    url: String,
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = "") },
            navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            },
            actions = {
                Text(modifier = Modifier.clickable {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                    )
                    context.startActivity(urlIntent)
                }, text = "浏览器打开")
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            MyWebView(url = url)
        }
    }
}