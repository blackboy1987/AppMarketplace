package com.cqzyzx.jpfx.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.TopBarTitle
import com.cqzyzx.jpfx.util.SharedPreferencesUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController,
) {
    val context = LocalContext.current
    var installAndDelete by remember {
        mutableStateOf(SharedPreferencesUtils(context).get("installAndDelete"))
    }
    var autoInstall by remember {
        mutableStateOf(SharedPreferencesUtils(context).get("autoInstall"))
    }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon(
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }, title = { TopBarTitle(text = "设置")})
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ){
                Text(text = "安装后删除安装包", modifier = Modifier.weight(1.0f))
                Switch(checked = installAndDelete=="true", onCheckedChange = {e->
                    run {
                        installAndDelete = "$e"
                        SharedPreferencesUtils(context).set("installAndDelete", "$e")
                    }
                })
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(text = "下载完成自动安装", modifier = Modifier.weight(1.0f))
                Switch(checked = autoInstall=="true", onCheckedChange = {e->
                    run {
                        autoInstall = "$e"
                        SharedPreferencesUtils(context).set("autoInstall", "$e")
                    }
                })
            }
        }
    }
}

