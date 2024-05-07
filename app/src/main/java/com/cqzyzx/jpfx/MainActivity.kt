package com.cqzyzx.jpfx

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.layout.PreSplash
import com.cqzyzx.jpfx.ui.layout.SplashAd
import com.cqzyzx.jpfx.util.AppInfoUtils
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import java.util.Date

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
        // 用来加载开屏广告
        setContent {
            // 添加一个状态来判断开屏广告的加载情况.(默认加载失败)
            var adSuccess by remember {
                mutableStateOf(false)
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SplashAd {
                    if(it!=0){
                        gotoMainActivity()
                    }else{
                        adSuccess = true
                    }
                }
                if(!adSuccess){
                    PreSplash()
                }
            }
        }
    }

    /**
     * 跳转至主页
     */
    private fun gotoMainActivity() {
        Log.e("SplashActivity", "MainActivity to HomeActivity 4 ${Date()}")
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        Log.e("SplashActivity", "MainActivity to HomeActivity ${Date()}")
    }

    /**
     *
     * 记录日志
     */
    private fun login(){
        val deviceInfo = AppInfoUtils.getDeviceInfo(this@MainActivity)
        val data = mapOf(
            "deviceId" to deviceInfo.deviceId,
            "model" to deviceInfo.model,
            "simSerialNumber" to deviceInfo.simSerialNumber,
            "os" to deviceInfo.os,
            "manufacturer" to deviceInfo.manufacturer,
            "token" to SharedPreferencesUtils(this@MainActivity).get("token")
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