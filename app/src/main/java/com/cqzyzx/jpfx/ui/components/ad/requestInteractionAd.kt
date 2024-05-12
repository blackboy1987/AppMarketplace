package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import com.cqzyzx.jpfx.entity.AdConfig
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 * 插屏广告：13902
 */
fun requestInteractionAd(context: Context, onClose:(status:String)->Unit) {
    val gson = Gson()
    val adConfig = gson.fromJson(SharedPreferencesUtils(context).get("adConfig"), AdConfig::class.java)
    val adId = adConfig.interAdId
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "4",
        "mediaId" to adId,
        "token" to SharedPreferencesUtils(context).get("token")
    )
    val adClient = AdClient(context as Activity)
    adClient.requestInteractionAd(adId, object : AdLoadAdapter() {
        override fun onAdShow(p0: SSPAd?) {
            super.onAdShow(p0)
            Log.e("requestInteractionAd", "onAdShow: $p0", )
            adData["status"] = "0"
            onClose("0")
            HttpUtils.adRequest(adData)
        }

        override fun onError(p0: Int, p1: String?) {
            super.onError(p0, p1)
            Log.e("requestInteractionAd", "onError: $p1", )
            adData["status"] = "-1"
            HttpUtils.adRequest(adData)
            onClose("-1")
        }

        override fun onAdClick(p0: SSPAd?) {
            super.onAdClick(p0)
            Log.e("requestInteractionAd", "onAdClick: $p0", )
        }

        override fun onAdDismiss(p0: SSPAd?) {
            super.onAdDismiss(p0)
            onClose("3")
            Log.e("requestInteractionAd", "onAdDismiss: $p0", )
        }
    })
}
