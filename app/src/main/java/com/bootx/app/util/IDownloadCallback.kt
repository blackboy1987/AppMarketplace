package com.bootx.app.util

/**
 * 网络请求的接口回调
 */
interface IDownloadCallback {

    /**
     * 下载完成
     */
    fun done()


    /**
     *
     */
    fun downloading(data: Int)


    /**
     *
     */
    fun error(e: Throwable)


    /**
     *
     */
    fun start()

}