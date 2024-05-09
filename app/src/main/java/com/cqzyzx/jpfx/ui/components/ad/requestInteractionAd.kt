package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.net.http.UrlRequest.Status
import android.util.Log
import com.cqzyzx.jpfx.config.AdConfig
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter
import java.util.Date

/**
 * 插屏广告：13902
 */
fun requestInteractionAd(context: Context, onClose:(status:String)->Unit) {
    val adId = AdConfig.INTER_AD_ID
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "4",
        "mediaId" to AdConfig.MEDIA_ID,
        "token" to SharedPreferencesUtils(context).get("token")
    )
    val adClient = AdClient(context as Activity)
    adClient.requestInteractionAd(AdConfig.INTER_AD_ID, object : AdLoadAdapter() {
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
    })
}
