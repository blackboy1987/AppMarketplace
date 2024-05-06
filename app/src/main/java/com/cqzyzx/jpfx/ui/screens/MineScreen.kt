package com.cqzyzx.jpfx.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cqzyzx.jpfx.ui.components.SoftIcon4
import com.cqzyzx.jpfx.ui.components.SoftIcon6
import com.cqzyzx.jpfx.ui.components.SoftIcon8
import com.cqzyzx.jpfx.ui.components.SoftIcon8_8
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.util.StoreManager
import com.cqzyzx.jpfx.viewmodel.MineViewModel
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MineScreen(
    navController: NavController,
    mineViewModel: MineViewModel= viewModel()
) {
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()
    val storeManager = StoreManager(context)
    LaunchedEffect(Unit) {
        mineViewModel.currentUser(context)
    }
    Scaffold(
        bottomBar = {
            if(SharedPreferencesUtils(context).get("token").isNotEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(modifier = Modifier.fillMaxWidth(),onClick = {
                        SharedPreferencesUtils(context).remove("token")
                        navController.navigate(Destinations.MainFrame.route+"/0")
                    }) {
                        Text(text = "退出")
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                ListItem(headlineContent = {
                    Text(text = "${mineViewModel.data.username}")
                }, leadingContent = {
                    SoftIcon4(url = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png")
                }, supportingContent = {
                    Text(text = "${mineViewModel.data.rankName}")
                })
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Column {
                    ListItem(headlineContent = {
                        Text(text = "基础功能", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    })
                    ListItem(modifier = Modifier
                        .clickable {
                            //navController.navigate(Destinations.AboutFrame.route)
                        }
                        .height(40.dp), headlineContent = {
                        Text(text = "关于我们", fontSize = 12.sp,)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    })
                    ListItem(modifier = Modifier
                        .clickable { }
                        .height(40.dp), headlineContent = {
                        Text(text = "设置", fontSize = 12.sp,)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    })
                    ListItem(modifier = Modifier
                        .clickable {
                            navController.navigate(Destinations.HistoryFrame.route)
                        }
                        .height(40.dp), headlineContent = {
                        Text(text = "历史记录", fontSize = 12.sp,)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    })
                    ListItem(modifier = Modifier
                        .clickable {
                            navController.navigate(Destinations.CollectLogFrame.route)
                        }
                        .height(40.dp), headlineContent = {
                        Text(text = "我的收藏", fontSize = 12.sp,)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    })
                    ListItem(modifier = Modifier
                        .clickable {
                            // 浏览记录清理掉
                            val gson = Gson()
                            coroutineScope.launch {
                                storeManager.save("keywords", gson.toJson(listOf<String>()))
                                mineViewModel.clearCache(context)
                            }
                        }
                        .height(40.dp), headlineContent = {
                        Text(text = "清理缓存", fontSize = 12.sp,)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    })
                    if(mineViewModel.data.upload==1){
                        ListItem(modifier = Modifier
                            .clickable {
                                navController.navigate(Destinations.TouGaoAppInfoListFrame.route)
                            }
                            .height(40.dp), headlineContent = {
                            Text(text = "上传应用", fontSize = 12.sp,)
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
    }
}
