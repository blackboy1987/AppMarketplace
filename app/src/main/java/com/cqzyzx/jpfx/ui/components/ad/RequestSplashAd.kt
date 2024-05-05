package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.cqzyzx.jpfx.R
import com.cqzyzx.jpfx.config.AdConfig
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 * 开屏广告: 3561
 */
@Composable
fun RequestSplashAd(context: Context,callback:(code: Int)->Unit) {

    val adId = AdConfig.SPLASH_AD_ID
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "6",
        "mediaId" to AdConfig.MEDIA_ID,
        "token" to SharedPreferencesUtils(context).get("token")
    )


    val adClient = AdClient(context as Activity)
    AndroidView(factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_splash, null)
        val mAdLayout = view.findViewById<FrameLayout>(R.id.ad_layout)
        val customLayout = view.findViewById<FrameLayout>(R.id.custom_layout)
        customLayout.visibility = View.INVISIBLE
        adClient.requestBannerAd(mAdLayout, AdConfig.SPLASH_AD_ID, object : AdLoadAdapter() {
            override fun onError(var1: Int, error: String) {
                super.onError(var1, error)
                Log.e("requestSplashAd", "onError: ${var1}, ${error}", )
                callback(-1)
                adData["status"] = "-1"
                HttpUtils.adRequest(adData)
            }
            override fun onAdLoad(p0: SSPAd?) {
                super.onAdLoad(p0)
                Log.e("requestSplashAd", "onAdLoad: ${p0}", )
                callback(0)
                adData["status"] = "0"
                HttpUtils.adRequest(adData)
            }
        })
        view
    })
}