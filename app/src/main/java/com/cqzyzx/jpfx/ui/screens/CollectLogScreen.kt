package com.cqzyzx.jpfx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cqzyzx.jpfx.ui.components.CollectLogItem
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.viewmodel.CollectLogViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun CollectLogScreen(
    navController: NavController,
    collectLogViewModel: CollectLogViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        collectLogViewModel.list(context)
    }

    Scaffold(
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        contentColor = Color.White,
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            }, title = {
                Text(text = "我的收藏")
            })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                itemsIndexed(collectLogViewModel.list) { index, item ->
                    CollectLogItem(item = item, onClick = {id ->
                        navController.navigate("${Destinations.AppDetailFrame.route}/$id")
                    }, onRemove = {id ->
                      coroutineScope.launch {
                          collectLogViewModel.delete(context,id)
                      }
                    })
                }
            }
        }
    }
}
