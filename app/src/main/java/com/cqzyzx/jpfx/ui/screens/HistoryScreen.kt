package com.cqzyzx.jpfx.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cqzyzx.jpfx.repository.entity.HistoryEntity
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.SoftIcon4
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("InvalidColorHexValue", "RememberReturnType")
@Composable
fun HistoryScreen(
    navController: NavHostController,
    historyViewModel: HistoryViewModel = viewModel(),
) {
    val context = LocalContext.current
    CommonUtils.ShowStatus((context as Activity).window)
    LaunchedEffect(Unit){
        historyViewModel.list(context)
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xfffafafa)
                ),
                title = {
                    Text(text = "历史记录")
                }, navigationIcon = {
                    LeftIcon {
                        navController.popBackStack()
                    }
                }, actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            historyViewModel.remove(context)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "",tint = Color(0xff737373))
                    }
                })
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight(),
            color = Color.White,
            contentColor = Color.White,
        ) {
            LazyColumn {
                item{
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if(historyViewModel.historyList.isEmpty()){

                }else{
                    itemsIndexed(historyViewModel.historyList){ index, item ->
                        Item11(item = item) {

                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Item11(item: HistoryEntity, modifier: Modifier = Modifier, onClick: (id: Int) -> Unit) {
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
                    }
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