package com.cqzyzx.jpfx.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.entity.SettingEntity
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.SoftIcon12
import com.cqzyzx.jpfx.ui.components.TopBarTitle
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var setting by remember {
        mutableStateOf(SettingEntity(
            name = "",
            logo = "",
        ))
    }
    LaunchedEffect(Unit) {
        val settingStr = SharedPreferencesUtils(context).get("setting")
        Log.e("AboutScreen", "AboutScreen111111: ${settingStr}")
        val gson = Gson()
        setting = gson.fromJson(settingStr, SettingEntity::class.java)
        Log.e("AboutScreen", "AboutScreen: ${setting.toString()}")
    }

    Scaffold(
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
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            SoftIcon12(url = setting.logo)
                        }
                    }
                }
                item {
                    Text(
                        text = "1.0.8",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
                item {
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "联系我们") })
                }
                item {
                    ListItem(modifier = Modifier
                        .clickable { }
                        .padding(start = 16.dp), headlineContent = { Text(text = "捐赠支持") })
                }
                item {
                    ListItem(modifier = Modifier
                        .clickable {
                            navController.navigate(Destinations.PageFrame.route+"/register/用户注册协议")
                        }
                        .padding(start = 16.dp),
                        headlineContent = { Text(text = "用户注册协议") })
                }
                item {
                    ListItem(modifier = Modifier
                        .clickable {
                            navController.navigate(Destinations.PageFrame.route+"/ys/隐私政策")
                        }
                        .padding(start = 16.dp), headlineContent = { Text(text = "隐私政策") })
                }
            }
        }

    }

}
