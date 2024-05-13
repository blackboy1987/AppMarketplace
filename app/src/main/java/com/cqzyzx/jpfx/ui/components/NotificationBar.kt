package com.cqzyzx.jpfx.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScrollableNotification(text: String, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val textWidth = text.length * 10f
    val animOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -textWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (textWidth * 100).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            ){
            Box(
                modifier = modifier.height(20.dp).fillMaxWidth().background(Color.Red)
            ) {
                Text(
                    text = text,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .offset(x = animOffset.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Box(
                modifier = Modifier.background(Color.White)
            ){
                Icon(tint=Color(0xff737373),imageVector = Icons.Default.NotificationImportant , contentDescription = "", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun ScrollableNotification1(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(tint=Color(0xff737373),imageVector = Icons.Default.NotificationImportant , contentDescription = "", modifier = Modifier.padding(start = 8.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1.0f),
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
@Composable
fun NotificationBar(title: String) {
    val text by remember { mutableStateOf(title) }
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        ScrollableNotification1(text = text)
    }
}