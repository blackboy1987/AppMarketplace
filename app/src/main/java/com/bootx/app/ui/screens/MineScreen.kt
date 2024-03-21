package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MineScreen(
    navController: NavHostController,
) {
    Scaffold(
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Text(text = "home")
        }
    }
}