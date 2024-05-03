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
        customLayout.visibility = View.VISIBLE
        adClient.requestBannerAd(mAdLayout, "12902", object : AdLoadAdapter() {
            override fun onError(var1: Int, error: String) {
                super.onError(var1, error)
                Log.e("requestSplashAd", "onError: ${var1}, ${error}", )
                callback(-1)
            }
            override fun onAdDismiss(ad: SSPAd) {
                super.onAdDismiss(ad)
                callback(0)
            }
        })
        view
    })
}