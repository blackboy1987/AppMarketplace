package com.bootx.yysc.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import kotlin.math.absoluteValue

val items = listOf(
    "https://imgwsdl.vivo.com.cn/appstore/topic/images/focusCollectionElementImg_836_1_20210601201449136.jpg",
    "https://imgwsdl.vivo.com.cn/appstore/topic/images/focusCollectionElementImg_49_1_20210518215859314.jpg",
    "https://imgwsdl.vivo.com.cn/appstore/topic/images/focusCollectionElementImg_691_1_20200907104042918.jpg",
    "https://imgwsdl.vivo.com.cn/appstore/topic/images/focusCollectionElementImg_836_1_20210601201449136.jpg",
    "https://imgwsdl.vivo.com.cn/appstore/topic/images/focusCollectionElementImg_49_1_20210518215859314.jpg",
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwiperItem() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = {
        items.size*1000
    },initialPage=items.size)
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal=24.dp),
    ) { page ->
        Box(

        ) {
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .height(160.dp)
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                model = items[page%items.size],
                contentDescription = ""
            )
        }
    }
}