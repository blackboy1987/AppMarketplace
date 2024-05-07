package com.cqzyzx.jpfx

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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

    override fun onResume() {
        super.onResume()
        // 应用程序进入前台
        Log.e("11111111111111111111111111111", "HomeActivity:onResume ", )
        // 在此处添加您的逻辑
        //gotoMainActivity()
    }

    override fun onPause() {
        super.onPause()
        // 应用程序进入后台
        Log.e("11111111111111111111111111111", "HomeActivity:onPause ", )
        // 在此处添加您的逻辑
    }

    /**
     * 跳转至主页
     */
    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}
