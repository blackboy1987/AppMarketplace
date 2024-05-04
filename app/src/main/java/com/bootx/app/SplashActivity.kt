package com.bootx.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.bootx.app.config.Config
import com.bootx.app.ui.components.NavHostApp
import com.bootx.app.util.AppInfoUtils.getDeviceInfo
import com.bootx.app.util.HttpUtils
import com.bootx.app.util.IHttpCallback
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.myapplication.ui.theme.AppMarketplaceTheme
import com.youxiao.ssp.core.SSPSdk

class SplashActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SSPSdk.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
        setContentView(R.layout.splash_screen)
        SSPSdk.init(this@SplashActivity, "6862", true)
        SSPSdk.init(this@SplashActivity, "6862", "0", true)
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


    /**
     *
     * 记录日志
     */
    private fun login(){
        val deviceInfo = getDeviceInfo(this@SplashActivity)
        val data = mapOf(
            "deviceId" to deviceInfo.deviceId,
            "model" to deviceInfo.model,
            "simSerialNumber" to deviceInfo.simSerialNumber,
            "os" to deviceInfo.os,
            "manufacturer" to deviceInfo.manufacturer,
            "token" to SharedPreferencesUtils(this@SplashActivity).get("token")
        )
        HttpUtils.get(
            data,
            Config.baseUrl + "/api/gather",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {

                }

                override fun onFailed(error: Any?) {
                }
            })
    }
}