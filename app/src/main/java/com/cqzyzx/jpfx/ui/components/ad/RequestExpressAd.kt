package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
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
 *模板信息流广告：13905
 */
@Composable
fun RequestExpressAd(context: Context) {
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfig::class.java)
    val adId = adConfig.interAdId
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "1",
        "mediaId" to adId,
        "token" to SharedPreferencesUtils(context).get("token")
    )


    val adClient = AdClient(context as Activity)
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_template, null)
        val findViewById = view.findViewById<FrameLayout>(R.id.ad_layout)
        adClient.requestExpressAd(adId, object : AdLoadAdapter() {
            override fun onAdLoad(ad: SSPAd) {
                super.onAdLoad(ad)
                findViewById.removeAllViews()
                findViewById.addView(ad.view)
            }

            override fun onError(i: Int, s: String) {
                super.onError(i, s)
                adData["status"] = "-1"
                HttpUtils.adRequest(adData)
            }

            override fun onAdShow(ad: SSPAd?) {
                super.onAdShow(ad)
                adData["status"] = "0"
                HttpUtils.adRequest(adData)
            }
        })
        view
    })
}
