package com.bootx.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bootx.app.ui.components.SoftIcon12
import com.bootx.app.ui.components.SoftIcon8_8
import com.bootx.app.ui.navigation.Destinations

@Composable
fun MineScreen(
    navController: NavController
) {
    LazyColumn() {
        item {
            ListItem(headlineContent = {
                Text(text = "blackboy")
            }, leadingContent = {
                SoftIcon8_8(url = "https://img.cnnb.com.cn/mixmedia/2024/01/26/3250/3b74b83e-9475-41b3-a3cd-336356be7116size_w_640_h_640.jpg")
            }, trailingContent = {
                Row(
                    Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                        .clickable { }
                        .width(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "",
                        tint = Color.White
                    )
                    Text(text = "签到", color = Color.White)
                }
            }, supportingContent = {
                Text(text = "注册用户")
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Column {
                ListItem(headlineContent = {
                    Text(text = "基础功能", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                })
                ListItem(modifier = Modifier
                    .clickable {
                        navController.navigate(Destinations.AboutFrame.route)
                    }
                    .height(40.dp), headlineContent = {
                    Text(text = "关于我们")
                }, trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = ""
                    )
                })
                ListItem(modifier = Modifier
                    .clickable { }
                    .height(40.dp), headlineContent = {
                    Text(text = "设置")
                }, trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = ""
                    )
                })
                ListItem(modifier = Modifier
                    .clickable { }
                    .height(40.dp), headlineContent = {
                    Text(text = "软件官网")
                }, trailingContent = {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = ""
                    )
                })
            }
        }
    }
}
