package com.cqzyzx.jpfx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.cqzyzx.jpfx.config.AdConfig
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.components.NavHostApp
import com.cqzyzx.jpfx.ui.layout.SplashAd
import com.cqzyzx.jpfx.ui.theme.AppMarketplaceTheme
import com.cqzyzx.jpfx.util.AppInfoUtils.getDeviceInfo
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.youxiao.ssp.core.SSPSdk

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
        SSPSdk.init(this@SplashActivity, AdConfig.MEDIA_ID, true)

        setContent {

            /**
             * -1： 显示加载页面
             * 0： 显示广告
             * 1： 显示主界面
             */
            var status by remember {
                mutableIntStateOf(0)
            }
            if(status == 1){
                AppMarketplaceTheme {
                    SharedPreferencesUtils(this@SplashActivity).set("adType6","-1")
                    NavHostApp()
                }
            }else{
                Surface {
                    Box(modifier = Modifier.fillMaxSize()) {
                        SplashAd { code ->
                            status = code
                            /**
                             * -1: 加载失败
                             * 0： 加载成功
                             * 1：加载成功之后隐藏
                             */
                            if (code == -1 || code == 1) {
                                // 显示主界面
                                status = 1
                            }
                        }
                    }
                    if (status == -1) {
                        //显示加载页面(因为开屏广告加载中，所以要遮住开屏广告的界面)
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column {
                                Icon(painter = painterResource(id = R.drawable.logo), contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
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
}