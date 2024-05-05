package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
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
 *横幅广告：1983 OK
 */
@Composable
fun RequestBannerAd(context: Context) {
    val adClient = AdClient(context as Activity)
    val adId = AdConfig.BANNER_AD_ID
    var adData = mutableMapOf(
        "adId" to adId,
        "adType" to "0",
        "mediaId" to AdConfig.MEDIA_ID,
        "token" to SharedPreferencesUtils(context).get("token")
    )
    AndroidView(factory = {
        val view = LayoutInflater.from(it).inflate(R.layout.activity_banner, null)
        val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)
        adClient.requestBannerAd(findViewById, AdConfig.BANNER_AD_ID, object : AdLoadAdapter() {
            override fun onError(i: Int, s: String) {
                super.onError(i, s)
                Log.e("requestBannerAd onError", "onAdLoad: $i,$s")
                adData["status"] = "0"
                HttpUtils.adRequest(adData)
            }

            override fun onAdShow(ad: SSPAd?) {
                super.onAdShow(ad)
                Log.e("requestBannerAd onAdShow", "onAdLoad: $ad")
                adData["status"] = "-1"
                HttpUtils.adRequest(adData)
            }
        })
        view
    })
}
