package com.cqzyzx.jpfx.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.azhon.appupdate.listener.OnDownloadListener
import com.azhon.appupdate.manager.DownloadManager
import com.cqzyzx.jpfx.R
import java.io.File

object DownloadUtils {

    fun downloadFile(context: Context, url: String, title: String, callback: IDownloadCallback) {
        val manager = DownloadManager.Builder(context as Activity).run {
            apkUrl(url).apkName("$title.apk").smallIcon(R.drawable.logo)
                .showNotification(false)
                .showBgdToast(false)
                .showNewerToast(false)
                .onDownloadListener(object:OnDownloadListener {
                    override fun cancel() {
                        Log.e("DownloadUtils", "cancel: " )
                        callback.cancel()
                    }
                    override fun done(apk: File) {
                        Log.e("DownloadUtils", "done: ${apk.absolutePath}" )
                        callback.done(apk)
                    }

                    override fun downloading(max: Int, progress: Int) {
                        Log.e("DownloadUtils", "downloading: ${max},${progress}" )
                        callback.downloading(max,progress)
                    }

                    override fun error(e: Throwable) {
                        Log.e("DownloadUtils", "error: ${e.message}" )
                        callback.error(e)
                    }

                    override fun start() {
                        Log.e("DownloadUtils", "start: " )
                        callback.start()
                    }

                })
        }.build()
        manager.download()
    }

}