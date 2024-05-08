package com.cqzyzx.jpfx.ui.components.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cqzyzx.jpfx.R
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
 * 开屏广告: 3561
 */
@Composable
fun RequestSplashAd(context: Context,callback:(code: Int)->Unit) {

    val adId = AdConfig.SPLASH_AD_ID
    val adData = mutableMapOf(
        "adId" to adId,
        "adType" to "6",
        "mediaId" to AdConfig.MEDIA_ID,
        "token" to SharedPreferencesUtils(context).get("token")
    )


    val adClient = AdClient(context as Activity)
    AndroidView(
        modifier = Modifier.fillMaxSize(),factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_splash, null)
        val mAdLayout = view.findViewById<FrameLayout>(R.id.ad_layout)
        mAdLayout.visibility = View.VISIBLE
        adClient.requestSplashAd(mAdLayout, AdConfig.SPLASH_AD_ID, object : AdLoadAdapter() {
            override fun onError(var1: Int, error: String) {
                super.onError(var1, error)
                Log.e("requestSplashAd", "onError: ${var1}, ${error}", )
                callback(-1)
                adData["status"] = "-1"
                HttpUtils.adRequest(adData)
            }
            override fun onAdLoad(p0: SSPAd?) {
                super.onAdLoad(p0)
                Log.e("requestSplashAd", "onAdLoad: ${p0}", )
                callback(0)
                adData["status"] = "0"
                HttpUtils.adRequest(adData)
            }

            override fun onStatus(p0: Int, p1: Int, p2: Int, p3: String?) {
                Log.e("requestSplashAd", "onStatus: ${p0},${p1},${p2},${p3},", )
                super.onStatus(p0, p1, p2, p3)
            }

            override fun onNext(p0: NextAdInfo?) {
                Log.e("requestSplashAd", "onNext: ${p0}", )
                super.onNext(p0)
            }

            override fun onAdClick(p0: SSPAd?) {
                Log.e("requestSplashAd", "onAdClick: ${p0}", )
                super.onAdClick(p0)
            }

            override fun onAdShow(p0: SSPAd?) {
                Log.e("requestSplashAd", "onAdShow: ${p0}", )
                super.onAdShow(p0)
            }

            override fun onAdDismiss(p0: SSPAd?) {
                Log.e("requestSplashAd", "onAdDismiss: ${p0}", )
                super.onAdDismiss(p0)
                callback(1)
                adData["status"] = "1"
                HttpUtils.adRequest(adData)
            }

            override fun onStartDownload(p0: String?) {
                Log.e("requestSplashAd", "onStartDownload: ${p0}", )
                super.onStartDownload(p0)
            }

            override fun onDownloadCompleted(p0: String?) {
                Log.e("requestSplashAd", "onDownloadCompleted: ${p0}", )
                super.onDownloadCompleted(p0)
            }

            override fun onStartInstall(p0: String?) {
                Log.e("requestSplashAd", "onStartInstall: ${p0}", )
                super.onStartInstall(p0)
            }

            override fun onInstallCompleted(p0: String?) {
                Log.e("requestSplashAd", "onInstallCompleted: ${p0}", )
                super.onInstallCompleted(p0)
            }
        })
        view
    })
}