package com.cqzyzx.jpfx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.entity.AdConfig
import com.cqzyzx.jpfx.service.CategoryService
import com.cqzyzx.jpfx.util.AppInfoUtils.getDeviceInfo
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.core.SSPSdk
import java.util.Date

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val categoryService = CategoryService.instance()

    var time: Long = 0
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting()
        login()
        loadAd()
        mySetContent()
        lifecycle.addObserver(AppLifecycleObserver())
    }

    private fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun clearContent(){
        setContent{
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Icon(painter = painterResource(id = R.drawable.logo), contentDescription = "")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun mySetContent() {
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Icon(painter = painterResource(id = R.drawable.logo), contentDescription = "")
                }
            }
        }
    }


    private inner class AppLifecycleObserver : LifecycleObserver {

        @RequiresApi(Build.VERSION_CODES.Q)
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onEnterForeground() {
            // 应用程序进入前台
            Log.e("AppLifecycleObserver", "onEnterForeground: 应用程序进入前台")
            if(time>1000*60){
                // 切换到后台超过一分钟
                clearContent()
                mySetContent()
            }
            time = 0L
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onEnterBackground() {
            // 应用程序进入后台
            time = Date().time
            Log.e("AppLifecycleObserver", "onEnterBackground: 应用程序进入后台")
        }
    }

    /**
     * 加载广告配置
     */
    private fun loadAd() {
        val deviceInfo = getDeviceInfo(this@SplashActivity)
        val data = mapOf(
            "deviceId" to deviceInfo.deviceId,
            "model" to deviceInfo.model,
            "simSerialNumber" to deviceInfo.simSerialNumber,
            "os" to deviceInfo.os,
            "manufacturer" to deviceInfo.manufacturer,
            "token" to SharedPreferencesUtils(this@SplashActivity).get("token")
        )
        HttpUtils.post(
            data,
            Config.baseUrl + "/api/adConfig",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    val toJson = data.toString()
                    SharedPreferencesUtils(this@SplashActivity).set("adConfig",toJson)
                    goMainActivity()
                }

                override fun onFailed(error: Any?) {
                    goMainActivity()
                }
            })
    }

    /**
     *
     * 记录日志
     */
    private fun login() {
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

    /**
     * 获取网站得相关配置信息
     */
    private fun setting() {
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
            Config.baseUrl + "/api/setting",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    val toJson = data.toString()
                    SharedPreferencesUtils(this@SplashActivity).set("settingConfig",toJson)
                }

                override fun onFailed(error: Any?) {
                }
            })
    }
}