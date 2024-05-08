package com.cqzyzx.jpfx.ui.layout

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.components.ad.RequestSplashAd
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import java.util.Date

@Composable
fun SplashAd(onLoad:(status: Int)->Unit) {
    val context = LocalContext.current
    var start = Date().time
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.fillMaxSize()){
            RequestSplashAd(context) {
                onLoad(it)
                Thread{
                    val data = mapOf("adType" to 0,"status" to it,"token" to SharedPreferencesUtils(context).get("token"))
                    HttpUtils.get(
                        data,
                        Config.baseUrl + "/api/ad/collection",
                        object : IHttpCallback {
                            override fun onSuccess(data: Any?) {
                                Log.e("RequestSplashAd", "onSuccess: ${data.toString()},$it", )
                            }
                            override fun onFailed(error: Any?) {
                            }
                        })
                }
                Log.e("SplashActivity", "MainActivity ad 3 ${Date()}")
            }
        }
    }
}