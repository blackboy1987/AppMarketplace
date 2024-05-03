package com.bootx.app.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter

/**
 *全屏视频：8811 OK
 */
fun requestFullScreenVideoAd(context: Context, onClose:(type:String)->Unit) {
    var status: String = ""
    val adClient = AdClient(context as Activity)
    adClient.requestFullScreenVideoAd("12907", object : AdLoadAdapter() {
        var gson = Gson()
        override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String) {
            Log.e("requestFullScreenVideoAd", "onStatus: $p0,$p1,$p2, $p3", )
            super.onStatus(p0, p1, p2, p3)
            status = "onStatus"
        }

        override fun onNext(p0: NextAdInfo?) {
            super.onNext(p0)
            status = "onNext"
            Log.e("requestFullScreenVideoAd", "onNext: $p0", )
        }

        override fun onAdLoad(p0: SSPAd?) {
            super.onAdLoad(p0)
            status = "onAdLoad"
            Log.e("requestFullScreenVideoAd", "onAdLoad: ${gson.toJson(gson)}", )
        }

        override fun onAdClick(p0: SSPAd?) {
            super.onAdClick(p0)
            status = "onAdClick"
            Log.e("requestFullScreenVideoAd", "onAdClick: $p0", )
        }

        override fun onAdShow(p0: SSPAd?) {
            super.onAdShow(p0)
            status = "onAdShow"
            Log.e("requestFullScreenVideoAd", "onAdShow: $p0", )
        }

        override fun onAdDismiss(p0: SSPAd?) {
            super.onAdDismiss(p0)
            status = "onAdDismiss"
            Log.e("requestFullScreenVideoAd", "onAdDismiss: $p0", )
        }

        override fun onStartDownload(p0: String?) {
            super.onStartDownload(p0)
            status = "onStartDownload"
            Log.e("requestFullScreenVideoAd", "onStartDownload: $p0", )
        }

        override fun onDownloadCompleted(p0: String?) {
            super.onDownloadCompleted(p0)
            status = "onDownloadCompleted"
            Log.e("requestFullScreenVideoAd", "onDownloadCompleted: $p0", )
        }

        override fun onStartInstall(p0: String?) {
            super.onStartInstall(p0)
            status = "onStartInstall"
            Log.e("requestFullScreenVideoAd", "onStartInstall: $p0", )
        }

        override fun onInstallCompleted(p0: String?) {
            super.onInstallCompleted(p0)
            status = "onInstallCompleted"
            Log.e("requestFullScreenVideoAd", "onInstallCompleted: $p0", )
        }

        override fun onError(p0: Int, p1: String?) {
            super.onError(p0, p1)
            status = "onError"
            Log.e("requestFullScreenVideoAd", "onError: $p1", )
        }
    })
}
