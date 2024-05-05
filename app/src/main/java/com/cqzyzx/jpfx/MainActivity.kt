package com.cqzyzx.jpfx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.components.ad.RequestSplashAd
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.ui.theme.AppMarketplaceTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("SplashActivity", "MainActivity start ${Date()}")
        super.onCreate(savedInstanceState)
        setContent {
            AppMarketplaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "MainActivity")
                }
            }
        }
        // 用来加载开屏广告
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Log.e("SplashActivity", "MainActivity ad 1 ${Date()}")
                RequestSplashAd(this@MainActivity) {
                    Log.e("SplashActivity", "MainActivity ad 2 ${Date()}")
                    Thread{
                        val data = mapOf("adType" to 0,"status" to it,"token" to SharedPreferencesUtils(this@MainActivity).get("token"))
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
        gotoMainActivity()
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
}