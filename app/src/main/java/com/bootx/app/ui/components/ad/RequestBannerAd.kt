package com.bootx.app.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.bootx.app.R
import com.bootx.app.entity.AdConfig
import com.bootx.app.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter

/**
 *横幅广告：1983 OK
 */
@Composable
fun RequestBannerAd(context: Context) {
    val adClient = AdClient(context as Activity)

    AndroidView(factory = {
        val view = LayoutInflater.from(it).inflate(R.layout.activity_banner, null)
        val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)

        val gson = Gson()
        val get = SharedPreferencesUtils(context).get("adConfig")
        val adConfig = gson.fromJson(get, AdConfig::class.java)

        adClient.requestBannerAd(findViewById, adConfig.bannerAdId, object : AdLoadAdapter() {
            override fun onAdLoad(ad: SSPAd) {
                Log.e("requestBannerAd onAdLoad", "onAdLoad: $ad")
                super.onAdLoad(ad)
            }

            override fun onError(i: Int, s: String) {
                super.onError(i, s)
                Log.e("requestBannerAd onError", "onAdLoad: $i,$s")
            }

            override fun onAdShow(ad: SSPAd?) {
                super.onAdShow(ad)
                Log.e("requestBannerAd onAdShow", "onAdLoad: $ad")
            }
        })
        view
    })
}
