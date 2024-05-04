package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.extension.onBottomReached
import com.bootx.app.ui.components.Item1
import com.bootx.app.ui.components.Loading1
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.viewmodel.AppViewModel
import com.bootx.app.viewmodel.DownloadViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AppScreen(
    navController: NavHostController,
    vm: AppViewModel = viewModel(),
    downloadViewModel: DownloadViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDropdownMenu by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        //获取分类列表
        vm.fetchList(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarTitle(text = "应用分类") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Destinations.SearchFrame.route)
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "")
                    }
                    IconButton(onClick = {
                        showDropdownMenu = true
                    }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "")
                    }
                }
            )
        }
    ) { contentPadding ->
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            vm.reload(context)
            refreshing = false
        }

        val state = rememberPullRefreshState(refreshing, ::refresh)
        val lazyListState = rememberLazyListState()
        lazyListState.onBottomReached(buffer = 3) {
            coroutineScope.launch {
                vm.loadMore(context)
            }
        }
        Box(modifier = Modifier.padding(contentPadding)) {
            Row {
                LazyColumn(
                    modifier = Modifier
                        .width(80.dp)
                        .padding(top = 16.dp)
                ) {
                    if(!vm.categoryLoading){
                        vm.categories.forEachIndexed { _, category ->
                            item {
                                CategoryItem(
                                    category,
                                    category.id == vm.currentIndex
                                ) { currentIndex ->
                                    coroutineScope.launch {
                                        vm.updateCurrentIndex(context, 1,currentIndex)
                                        //lazyListState.animateScrollToItem(0)
                                    }
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight()
                        .padding(top = 16.dp)
                        .pullRefresh(state)
                ) {
                    if (vm.softListLoading) {
                        Loading1()
                    }
                    LazyColumn(
                        state = lazyListState,
                    ) {
                        items(vm.softList) { item ->
                           key(item.id){
                               Item1(item, modifier = Modifier.padding(end = 8.dp), onClick = { id ->
                                   navController.navigate("${Destinations.AppDetailFrame.route}/$id")
                               })
                           }
                        }
                    }
                    PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.Center))
                }
            }

        }
    }
}

@Composable
fun CategoryItem(category: CategoryEntity, selected: Boolean, click: (id: Int) -> Unit) {
    Button(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.width(80.dp),
        onClick = {
            click(category.id)
        },
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 18.dp,
            bottomEnd = 18.dp,
            bottomStart = 0.dp,
        ),
        colors = ButtonDefaults.textButtonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Color(0xfffafafa),
            contentColor = Color(0xff000000),
        ),
        enabled = !selected,
    ) {
        Text(
            text = category.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
        )
    }
}