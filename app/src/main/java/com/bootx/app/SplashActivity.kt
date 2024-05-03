package com.bootx.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.bootx.app.util.CommonUtils
import com.youxiao.ssp.core.SSPSdk
import java.util.Date

class SplashActivity : ComponentActivity() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SSPSdk.attachBaseContext(base);
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("SplashActivity", "SplashActivity start ${Date()}")
        super.onCreate(savedInstanceState)
        SSPSdk.init(this@SplashActivity, "6862", true)
        SSPSdk.init(this@SplashActivity, "6862", null, true)
        SSPSdk.setReqPermission(true)
        toMainActivity()
    }

    /**
     * 跳转至主页
     */
    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        Log.e("SplashActivity", "SplashActivity to  MainActivity ${Date()}")
    }
}