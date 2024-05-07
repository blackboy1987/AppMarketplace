package com.cqzyzx.jpfx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.util.AppInfoUtils.getDeviceInfo
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.youxiao.ssp.core.SSPSdk

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        //SSPSdk.attachBaseContext(newBase)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
        setContentView(R.layout.splash_screen)
        SSPSdk.init(this@SplashActivity, "7039", true)
        SSPSdk.init(this@SplashActivity, "7039", "0", true)
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