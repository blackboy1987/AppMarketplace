package com.bootx.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.SoftIcon12
import com.bootx.app.ui.components.TopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon(
                   onClick = {
                       navController.popBackStack()
                   }
                )
            }, title = { TopBarTitle(text = "关于")})
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()){
                        Box(modifier = Modifier.align(Alignment.Center)){
                            SoftIcon12(url = "https://img.cnnb.com.cn/mixmedia/2024/01/26/3250/3b74b83e-9475-41b3-a3cd-336356be7116size_w_640_h_640.jpg")
                        }
                    }
                }
                item{
                    Text(text = "1.0.8", modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), textAlign = TextAlign.Center, fontSize = 16.sp)
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "更新日志") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "开发者说") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "项目开发动态") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "联系我们") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "致谢名单") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "捐赠支持") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "用户注册协议") })
                }
                item{
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "隐私政策") })
                }
                item{
                    ListItem(modifier = Modifier.clickable {  }, headlineContent = { Text(text = "更新日志") })
                }
            }
        }

    }

}
