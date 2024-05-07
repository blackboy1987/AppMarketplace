package com.cqzyzx.jpfx

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.cqzyzx.jpfx.ui.components.NavHostApp
import com.cqzyzx.jpfx.ui.theme.AppMarketplaceTheme
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class HomeActivity : ComponentActivity(){
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        SharedPreferencesUtils(this@HomeActivity).set("homeIndex","0")
        setContent {
            AppMarketplaceTheme {
                SharedPreferencesUtils(this@HomeActivity).set("adType6","-1")
                NavHostApp()
            }
        }
    }
}
