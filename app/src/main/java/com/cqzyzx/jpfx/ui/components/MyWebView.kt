package com.cqzyzx.jpfx.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

/**
 *横幅广告：1983 OK
 */
@Composable
fun MyWebView(url: String) {
    AndroidView(factory = { context->
        val webView = WebView(context)
        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient(){}
            loadUrl(url)
        }
        webView
    })
}
