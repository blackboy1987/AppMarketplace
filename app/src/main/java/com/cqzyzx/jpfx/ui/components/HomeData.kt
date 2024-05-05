package com.cqzyzx.jpfx.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cqzyzx.jpfx.entity.SoftEntity
import kotlin.math.abs

@Composable
fun Item1(item: SoftEntity, modifier: Modifier = Modifier, onClick: (id: Int) -> Unit) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    val scale = remember { Animatable(0.3f) }
    LaunchedEffect(Unit) {
        isVisible = true
    }
    LaunchedEffect(isVisible) {
        scale.animateTo(if (isVisible) 1f else 0f, animationSpec = tween(durationMillis = 400))
    }
    Box(
        modifier = Modifier
            .then(modifier)
            .clickable {
                onClick(item.id)
            }
            .fillMaxWidth()
            .scale(scale.value)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            SoftIcon4(url = "${item.logo}")
            ConstraintLayout(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(start = 8.dp)
            ) {
                val (title, title1, title2) = createRefs()
                Box(modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 0.dp)
                        start.linkTo(parent.start, margin = 0.dp)
                    }) {
                    Text(
                        text = "${item.name}",
                        color = Color(0xff101010),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(title1) {
                            top.linkTo(title.bottom, (-10).dp)
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "实用工具", color = Color(0xffa6a6a6), fontSize = 10.sp)
                        Text(text = "-", color = Color(0xffa6a6a6), fontSize = 10.sp)
                        Text(text = "下载工具", color = Color(0xffa6a6a6), fontSize = 10.sp)
                        Text(text = " | ", color = Color(0xffa6a6a6), fontSize = 10.sp)
                        Icon(
                            imageVector = Icons.Default.RemoveRedEye,
                            contentDescription = "",
                            tint = Color(0xffa6a6a6),
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = " ${item.downloads}",
                            color = Color(0xffa6a6a6),
                            fontSize = 10.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(title2) {
                            top.linkTo(title1.bottom, (-10).dp)
                        }
                ) {
                    Text(text = "${item.versionName}", color = Color(0xffa6a6a6), fontSize = 10.sp)
                }
            }
            Card(
                modifier = Modifier.width(60.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                border = BorderStroke(0.5.dp, Color(0xff4488eb)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "查看",
                    color = Color(0xff4488eb),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTabIndicator1(
    tabPositions: List<TabPosition>,
    pagerState: PagerState,
    color: Color = MaterialTheme.colorScheme.primary,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 0.4f,
    height: Dp = 5.dp,
) {
    val currentPage by rememberUpdatedState(newValue = pagerState.currentPage)
    val fraction by rememberUpdatedState(newValue = pagerState.currentPageOffsetFraction)
    val currentTab = tabPositions[currentPage]
    val previousTab = tabPositions.getOrNull(currentPage - 1)
    val nextTab = tabPositions.getOrNull(currentPage + 1)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val indicatorWidth = currentTab.width.toPx() * percent
            val indicatorOffset = if (fraction > 0 && nextTab != null) {
                lerp(currentTab.left, nextTab.left, fraction).toPx()
            } else if (fraction < 0 && previousTab != null) {
                lerp(currentTab.left, previousTab.left, -fraction).toPx()
            } else {
                currentTab.left.toPx()
            }
            val canvasHeight = size.height
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
                    canvasHeight - height.toPx()
                ),
                size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
                cornerRadius = CornerRadius(50f)
            )
        }
    )
}