package com.bootx.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bootx.app.entity.AppInfo
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.viewmodel.TouGaoListViewModel

@Composable
fun TouGaoAppInfoListScreen(
    navController: NavHostController,
    touGaoListViewModel: TouGaoListViewModel = viewModel()
) {
    val context = LocalContext.current
    var searchStatus by remember {
        mutableStateOf(false)
    }
    var keywords by remember {
        mutableStateOf("")
    }
    var appInfoList by remember { mutableStateOf(listOf<AppInfo>()) }

    LaunchedEffect(Unit) {
        appInfoList = touGaoListViewModel.getAppList(context)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                TopBarTitle(text = "选择应用")
            }, navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            })
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn {
                items(appInfoList.filter { item -> item.appName.indexOf(keywords) >= 0 }) {
                    ListItem(
                        modifier = Modifier.clickable {
                                                      navController.navigate(Destinations.TouGaoFrame.route+"/${it.packageName}")
                        },
                        headlineContent = {
                            Text(
                                text = it.appName,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        supportingContent = {
                            SelectionContainer{
                                Text(
                                    text = it.packageName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        leadingContent = {
                            AsyncImage(
                                modifier = Modifier.size(60.dp),
                                model = it.appIcon,
                                contentDescription = ""
                            )
                        }
                    )
                }
            }
        }
    }
}
