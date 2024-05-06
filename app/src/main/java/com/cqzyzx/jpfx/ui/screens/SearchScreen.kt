package com.cqzyzx.jpfx.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cqzyzx.jpfx.extension.onBottomReached
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.SoftItem
import com.cqzyzx.jpfx.ui.navigation.Destinations
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.StoreManager
import com.cqzyzx.jpfx.viewmodel.SearchViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = viewModel()
) {
    val context = LocalContext.current
    var searchStatus by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val storeManager = StoreManager(context)
    var keywords by remember {
        mutableStateOf<String>("天涯")
    }
    var showDialog by remember {
        mutableStateOf<Boolean>(false)
    }
    val gson = Gson()

    val searchResult = storeManager.get("keywords").collectAsState(initial = "[]")

    LaunchedEffect(Unit) {
        searchViewModel.hotSearch(context)
    }


    fun add(value: String) {
        val type = object : TypeToken<List<String>>() {}.type
        val list: List<String> = try {
            Gson().fromJson(searchResult.value, type)
        } catch (e: Exception) {
            listOf()
        }
        val newList = list.filter { text -> text != value }
        val softList = mutableListOf<String>()
        softList.add(value)
        softList.addAll(newList)
        coroutineScope.launch {
            storeManager.save("keywords", gson.toJson(softList))
        }
    }

    fun get(): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        val list: List<String> = try {
            Gson().fromJson(searchResult.value, type)
        } catch (e: Exception) {
            listOf()
        }
        return list
    }

    fun clear() {
        coroutineScope.launch {
            storeManager.save("keywords", gson.toJson(listOf<String>()))
        }
    }

    fun search(keyword: String) {
        keywords = keyword
        add(keywords)
        searchStatus = true
        coroutineScope.launch {
            searchViewModel.search(context, keywords, 1)
        }
    }

    val lazyListState = rememberLazyListState()
    lazyListState.onBottomReached(buffer = 3) {
        coroutineScope.launch {
            if (searchViewModel.hasMore) {
                searchViewModel.search(
                    context,
                    keywords,
                    searchViewModel.pageNumber
                )
            }
        }
    }
    Scaffold(
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        contentColor = Color.White,
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon {
                    if (keywords.isEmpty()) {
                        navController.popBackStack()
                    } else {
                        keywords = ""
                        searchStatus = false
                    }
                }
            }, title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Card(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Destinations.SearchFrame.route)
                                }
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors().copy(
                                containerColor = Color.White,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0xffd5d5d5))
                        ) {
                            BasicTextField(
                                singleLine = true,
                                value = keywords,
                                onValueChange = { value -> keywords = value },
                                modifier = Modifier
                                    .height(40.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .border(1.dp, Color(0xffd5d5d5))
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp)
                                    .wrapContentHeight(
                                        align = Alignment.CenterVertically,
                                    )
                            )
                        }
                    }
                }
            }, actions = {
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "")
                }
            })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (!searchStatus) {
                val filters = get().filter { text -> text.isNotEmpty() }
                FlowRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { title ->
                        Card(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .combinedClickable(
                                    onClick = {
                                        keywords = title
                                        search(keywords)
                                    },
                                    onLongClick = {
                                        keywords = title + title + title
                                    },
                                ),
                            shape = RoundedCornerShape(4.dp)

                        ) {
                            Text(text = title, modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            } else {
                if (searchViewModel.list.isEmpty()) {
                    CommonUtils.toast(context, "未找到相关应用")
                }
                LazyColumn(
                    state = lazyListState,
                ) {
                    itemsIndexed(searchViewModel.list) { index, item ->
                        SoftItem(item = item) { id ->
                            navController.navigate("${Destinations.AppDetailFrame.route}/$id")
                        }
                    }
                }
            }
        }
    }
    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showDialog = false
            }, modifier = Modifier
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "说明",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1.对当前搜索有结果有操作之后，才会加入历史记录",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Text(
                    text = "2.当输入框有文字时，返回键会清空文字，再次返回才会返回到上一界面",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Text(
                    text = "3.长按可删除某一搜索历史",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    showDialog = false
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "关闭")
                }
            }
        }
    }

}
