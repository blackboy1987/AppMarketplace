package com.bootx.app.util

import com.azhon.appupdate.listener.OnDownloadListener
import java.io.File

/**
 * 网络请求的接口回调
 */
interface IDownloadCallback: OnDownloadListener {
    override fun cancel() {
    }

    override fun done(apk: File) {
    }

    override fun downloading(max: Int, progress: Int) {
    }

    override fun error(e: Throwable) {
    }

    override fun start() {
    }


}