package com.bootx.app.ui.components.ad

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.bootx.app.entity.AdConfig
import com.bootx.app.util.SharedPreferencesUtils
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.RewardVideoAdAdapter

/**
*激励视频广告：1028
*/
fun requestRewardAd(context: Context, onClose:(type:String)->Unit) {
    val adClient = AdClient(context as Activity)
    adClient.requestRewardAd("12909", object : RewardVideoAdAdapter() {
        override fun loadRewardAdSuc(sspAd: SSPAd?) {
            super.loadRewardAdSuc(sspAd)
            onClose("loadRewardAdSuc")
        }

        override fun playRewardVideoCompleted(type: SSPAd) {
            super.playRewardVideoCompleted(type)
            onClose("playRewardVideoCompleted")
            Toast.makeText(context, "激励视频播放完成", Toast.LENGTH_SHORT).show()
        }

        override fun onReward(p0: SSPAd?, p1: Boolean, p2: MutableMap<String, Any>?) {
            super.onReward(p0, p1, p2)
            onClose("onReward")
        }

        override fun loadRewardAdFail(s: String) {
            super.loadRewardAdFail(s)
            Toast.makeText(context, "请求激励视频广告失败:$s", Toast.LENGTH_SHORT).show()
            onClose("loadRewardAdFail")
        }

        override fun loadRewardVideoFail(i: Int, i1: Int) {
            super.loadRewardVideoFail(i, i1)
            Toast.makeText(context, "加载激励视频失败", Toast.LENGTH_SHORT).show()
            onClose("loadRewardVideoFail")
        }
    })
}