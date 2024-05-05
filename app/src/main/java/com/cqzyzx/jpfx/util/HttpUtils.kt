package com.cqzyzx.jpfx.util

import android.util.Log
import com.cqzyzx.jpfx.config.Config
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


object HttpUtils {
    private val mClient = OkHttpClient.Builder()
        .callTimeout(100, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定,
        .connectTimeout(100, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(100, TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(100, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//重连
        .followRedirects(false)//重定向
        .cache(Cache(File("sdcard/cache", "okhttp"), 1024))
        .cookieJar(LocalCookieJar())
        //.addNetworkInterceptor(HeaderInterceptor())//公共header的拦截器
        .build()

    fun get(params: Map<String, Any>, urlStr: String, callback: IHttpCallback) {
        val urlBuilder = urlStr.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }

        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        val newCall = mClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }

    fun post(body: Map<String,String>, urlStr: String, callback: IHttpCallback) {

        val builder = FormBody.Builder()
        body.keys.forEach { key->
            body[key]?.let { builder.add(key, it) }
        }
        val formBody = builder.build()
        val request = Request.Builder()
            .post(formBody)
            .url(urlStr)
            .tag(body)
            .build()
        val newCall = mClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }

        })
    }

    fun adRequest(body: Map<String,String>) {
        val builder = FormBody.Builder()
        body.keys.forEach { key->
            body[key]?.let { builder.add(key, it) }
        }
        val formBody = builder.build()
        val request = Request.Builder()
            .post(formBody)
            .url(Config.baseUrl+"/api/adLog/add")
            .tag(body)
            .build()
        val newCall = mClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("adRequest", "onFailure: ${e.message}", )
            }
            override fun onResponse(call: Call, response: Response) {
                Log.e("adRequest", "onFailure: ${response.message}", )
            }

        })
    }
}