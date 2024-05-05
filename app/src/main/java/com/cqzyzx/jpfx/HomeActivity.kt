package com.cqzyzx.jpfx

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.cqzyzx.jpfx.ui.components.NavHostApp
import com.cqzyzx.jpfx.ui.theme.AppMarketplaceTheme
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import java.util.Date

class HomeActivity : ComponentActivity(){
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMarketplaceTheme {
                SharedPreferencesUtils(this@HomeActivity).set("adType6","-1")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHostApp()
                }
            }
        }
    }
}
