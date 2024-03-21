package com.bootx.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import com.bootx.app.config.Config
import com.bootx.app.entity.AdConfig
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.HttpUtils
import com.bootx.app.util.IHttpCallback
import com.bootx.app.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.core.SSPSdk

class MainActivity : ComponentActivity() {

    // 广告区域容器
    private var mAdLayout: ViewGroup? = null

    // 自定义宣传内容
    private var customLayout: FrameLayout? = null

    // 展示样式
    var showStyle = 0

    // 展示样式示例：全屏、非全屏，底部自定义内容样式
    private val SHOW_STYLE = "SHOW_STYLE"

    // 非全屏
    val SHOW_STYLE_NOT_FULL = 0x11

    // 底部自定义内容
    val SHOW_STYLE_CUSTOM_SCREEN = 0x12

    // 动态权限
    private var mDynamicPermissions: List<String> = listOf()


    // 动态权限请求吗
    private var REQUEST_CODE = 0x02


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SSPSdk.attachBaseContext(base);
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        //
        if (networkCapabilities != null) {
            if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)){
                // 不是表示网络不是虚拟专用网。
            }else{
                // 是虚拟网络
                CommonUtils.toast(this@MainActivity,"虚拟网络")
            }
        }



        HttpUtils.post(
            "{}",
            Config.baseUrl + "/api/adConfig",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    try {
                        val gson = Gson()
                        val adConfig = gson.fromJson("${data}", AdConfig::class.java)
                        SharedPreferencesUtils(this@MainActivity).set(
                            "adConfig",
                            gson.toJson(adConfig)
                        )
                        Log.e("adConfig", "onSuccess: ${data.toString()}")
                        SSPSdk.init(this@MainActivity, adConfig.mediaId, true)
                        SSPSdk.init(this@MainActivity, adConfig.mediaId, null, true)
                        SSPSdk.setReqPermission(true)
                    } catch (e: Exception) {
                        Log.e("adConfig", "onSuccess: 广告配置失败：${data}")
                    }


                }

                override fun onFailed(error: Any?) {
                    Log.e("adConfig", "onFailed: 广告配置失败：${error.toString()}")
                }

            })



        gotoMainActivity()
    }

    /**
     * 跳转至主页
     */
    private fun gotoMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}