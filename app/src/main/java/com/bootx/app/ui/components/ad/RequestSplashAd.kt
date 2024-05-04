package com.bootx.app.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.bootx.app.R
import com.youxiao.ssp.ad.bean.NextAdInfo
import com.youxiao.ssp.ad.bean.SSPAd
import com.youxiao.ssp.ad.core.AdClient
import com.youxiao.ssp.ad.listener.AdLoadAdapter

/**
 * 开屏广告: 3561
 */
@Composable
fun RequestSplashAd(context: Context,callback:(code: Int)->Unit) {
    val adClient = AdClient(context as Activity)

    AndroidView(factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_splash, null)
        val mAdLayout = view.findViewById<FrameLayout>(R.id.ad_layout)
        val customLayout = view.findViewById<FrameLayout>(R.id.custom_layout)
        customLayout.visibility = View.INVISIBLE
        adClient.requestBannerAd(mAdLayout, "12902", object : AdLoadAdapter() {
            override fun onError(var1: Int, error: String) {
                super.onError(var1, error)
                Log.e("requestSplashAd", "onError: ${var1}, ${error}", )
                callback(-1)
            }
            override fun onAdDismiss(ad: SSPAd) {
                super.onAdDismiss(ad)
                Log.e("requestSplashAd", "onAdDismiss: ${ad}", )
                callback(0)
            }

            override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
                super.onStatus(p0, p1, p2, p3)
                Log.e("requestSplashAd", "onStatus: ${p0}, ${p1}, ${p3}", )
                callback(0)
            }

            override fun onNext(p0: NextAdInfo?) {
                super.onNext(p0)
                Log.e("requestSplashAd", "onNext: ${p0}", )
                callback(0)
            }

            override fun onAdLoad(p0: SSPAd?) {
                super.onAdLoad(p0)
                Log.e("requestSplashAd", "onAdLoad: ${p0}", )
                callback(0)
            }

            override fun onAdClick(p0: SSPAd?) {
                super.onAdClick(p0)
                Log.e("requestSplashAd", "onAdClick: ${p0}", )
                callback(0)
            }

            override fun onAdShow(p0: SSPAd?) {
                super.onAdShow(p0)
                Log.e("requestSplashAd", "onError: ${p0}", )
                callback(0)
            }

            override fun onStartDownload(p0: String?) {
                super.onStartDownload(p0)
                Log.e("requestSplashAd", "onStartDownload: ${p0}", )
                callback(0)
            }

            override fun onDownloadCompleted(p0: String?) {
                super.onDownloadCompleted(p0)
                Log.e("requestSplashAd", "onDownloadCompleted: ${p0}", )
                callback(0)
            }

            override fun onStartInstall(p0: String?) {
                super.onStartInstall(p0)
                Log.e("requestSplashAd", "onStartInstall: ${p0}")
                callback(0)
            }

            override fun onInstallCompleted(p0: String?) {
                super.onInstallCompleted(p0)
                Log.e("requestSplashAd", "onInstallCompleted: ${p0}", )
                callback(0)
            }
        })
        view
    })
}