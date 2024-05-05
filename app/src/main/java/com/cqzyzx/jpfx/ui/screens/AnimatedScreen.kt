package com.cqzyzx.jpfx.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cqzyzx.jpfx.ui.components.LeftIcon
import com.cqzyzx.jpfx.ui.components.TopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                LeftIcon(
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }, title = { TopBarTitle(text = "动画") })
        }
    ) {

        var show by remember {
            mutableStateOf(false)
        }
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Text(text = "动画", modifier = Modifier.clickable {
                show = !show
            })
            AnimatedVisibility(
                visible = show,
                enter = slideInVertically() + fadeIn(), // 水平方向划入 + 渐变展示
                exit = slideOutHorizontally() + fadeOut() // 垂直方向划出 + 渐变隐藏
            ) {
                Text(text = "adfadsfadkslfjal;dsfjads;lfjas;dlkfja;dslfjas;dlfjads;lfjads;lfjads;lfjdsa")
            }


            Box(modifier = Modifier
                .fillMaxWidth()){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier.background(Color.White).width(50.dp).height(50.dp),
                        colors = CardDefaults.cardColors().copy(
                            containerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(100),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 8.dp,
                            focusedElevation = 8.dp,
                            hoveredElevation = 8.dp,
                            draggedElevation = 8.dp,
                            disabledElevation = 8.dp,
                        ),
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(12.dp),
                            color = Color.Black,
                        )
                    }
                }
            }

        }
    }

}
