package com.bootx.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Loading(title: String="") {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            modifier = Modifier.size(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
                if(title.isNotBlank()){
                    Text(text = title)
                }
            }
        }
    }
}


@Composable
fun Loading1(){
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