package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cqzyzx.jpfx.R
import com.cqzyzx.jpfx.entity.AdConfig
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter

/**
 *横幅广告：1983 OK
 */
@Composable
fun RequestBannerAd(context: Context) {
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfig::class.java)
    val adClient = AdClient(context as Activity)
    val adId = adConfig.bannerAdId
    var adData = mutableMapOf(
        "adId" to adId,
        "adType" to "0",
        "mediaId" to adConfig.bannerAdId,
        "token" to SharedPreferencesUtils(context).get("token")
    )
    AndroidView(
        modifier = Modifier.fillMaxWidth(),factory = {
        val view = LayoutInflater.from(it).inflate(R.layout.activity_banner, null)
        val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)
        adClient.requestBannerAd(findViewById, adConfig.bannerAdId, object : AdLoadAdapter() {
            override fun onError(i: Int, s: String) {
                super.onError(i, s)
                Log.e("requestBannerAd onError", "onError: $i,$s")
                adData["status"] = "0"
                HttpUtils.adRequest(adData)
            }

            override fun onAdShow(ad: SSPAd?) {
                super.onAdShow(ad)
                Log.e("requestBannerAd onAdShow", "onAdShow: $ad")
                adData["status"] = "-1"
                HttpUtils.adRequest(adData)
            }
        })
        view
    })
}
