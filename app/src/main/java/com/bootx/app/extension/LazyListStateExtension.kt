package com.bootx.app.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun LazyListState.onBottomReached(buffer:Int = 1,loadMore:()->Unit) {

    require(buffer>=0){"buffer 值必须是大于等于0"}

    // 是否应该加载更多的状态
    val shouldLoadMore = remember {
        // 由另一个状态计算派生
        derivedStateOf {
            // 获取最后显示的 item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?: return@derivedStateOf true

            // 如果最后显示的item是最后一个的话，说明已经触底，需要加载更多
            lastVisibleItem.index >= layoutInfo.totalItemsCount-1-buffer
        }
    }
    LaunchedEffect(shouldLoadMore){
        snapshotFlow{shouldLoadMore.value}.collect{
            if(it){
                loadMore()
            }
        }

    }
}

@Composable
fun LazyListState.onScroll(callback:(index: Int)->Unit) {

    // 是否应该加载更多的状态
    val index = remember {
        // 由另一个状态计算派生
        derivedStateOf {
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()?: return@derivedStateOf layoutInfo.visibleItemsInfo.first().index
            firstVisibleItem.index
        }
    }
    LaunchedEffect(index){
        snapshotFlow{index.value}.collect{
            callback(index.value)
        }

    }
}