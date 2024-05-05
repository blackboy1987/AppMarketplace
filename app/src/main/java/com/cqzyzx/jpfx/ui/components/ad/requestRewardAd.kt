package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.cqzyzx.jpfx.config.AdConfig
import com.cqzyzx.jpfx.util.HttpUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.RewardVideoAdAdapter
import java.util.Date

/**
*激励视频广告：13903
*/
fun requestRewardAd(context: Context, onClose:(type:String)->Unit) {
    val adId = AdConfig.REWARD_VIDEO_AD_ID
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "5",
        "mediaId" to AdConfig.REWARD_VIDEO_AD_ID,
        "token" to SharedPreferencesUtils(context).get("token")
    )

    val adClient = AdClient(context as Activity)
    adClient.requestRewardAd(AdConfig.REWARD_VIDEO_AD_ID, object : RewardVideoAdAdapter() {
        override fun loadRewardAdSuc(sspAd: SSPAd?) {
            super.loadRewardAdSuc(sspAd)
            onClose("loadRewardAdSuc")
            adData["status"] = "0"
            HttpUtils.adRequest(adData)
        }

        override fun onReward(p0: SSPAd?, p1: Boolean, p2: MutableMap<String, Any>?) {
            super.onReward(p0, p1, p2)
            onClose("onReward")
            adData["status"] = "1"
            HttpUtils.adRequest(adData)
        }

        override fun loadRewardAdFail(s: String) {
            super.loadRewardAdFail(s)
            Toast.makeText(context, "请求激励视频广告失败:$s", Toast.LENGTH_SHORT).show()
            onClose("loadRewardAdFail")
            adData["status"] = "-1"
            HttpUtils.adRequest(adData)
        }

        override fun loadRewardVideoFail(i: Int, i1: Int) {
            super.loadRewardVideoFail(i, i1)
            Toast.makeText(context, "加载激励视频失败", Toast.LENGTH_SHORT).show()
            onClose("loadRewardVideoFail")
            adData["status"] = "-2"
            HttpUtils.adRequest(adData)
        }
    })
}