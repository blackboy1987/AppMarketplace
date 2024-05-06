package com.cqzyzx.jpfx

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cqzyzx.jpfx.config.Config
import com.cqzyzx.jpfx.ui.components.ad.RequestSplashAd
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.IHttpCallback
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.ui.theme.AppMarketplaceTheme
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
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
            var showClose by mutableStateOf(false)
            fun countDownTimer2() {
                var num = 5
                val timer = Timer()
                val timeTask = object : TimerTask() {
                    override fun run() {
                        num--
                        if (num < 0) {
                            timer.cancel()
                            showClose = true
                        }
                    }
                }
                timer.schedule(timeTask, 1000, 1000)
            }


            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Log.e("SplashActivity", "MainActivity ad 1 ${Date()}")
                Box(modifier = Modifier.fillMaxSize()){
                    Box(modifier = Modifier.fillMaxSize()){
                        RequestSplashAd(this@MainActivity) {
                            Log.e("SplashActivity", "MainActivity ad 2 ${Date()}")
                            if(it!=0){
                                gotoMainActivity()
                            }else{
                                countDownTimer2()
                            }
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
                    if(showClose){
                        Box(){
                            IconButton(onClick = {
                                gotoMainActivity()
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "")
                            }
                        }
                    }
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
}