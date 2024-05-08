package com.cqzyzx.jpfx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.cqzyzx.jpfx.config.AdConfig
import com.youxiao.ssp.core.SSPSdk

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        //SSPSdk.attachBaseContext(newBase)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        SSPSdk.init(this@SplashActivity, AdConfig.MEDIA_ID, true)
        SSPSdk.init(this@SplashActivity, AdConfig.MEDIA_ID, "0", true)
        gotoMainActivity()
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