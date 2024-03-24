package com.bootx.app.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.azhon.appupdate.config.Constant
import com.azhon.appupdate.listener.OnDownloadListener
import com.azhon.appupdate.manager.DownloadManager
import com.azhon.appupdate.util.NotificationUtil
import com.bootx.app.R
import com.bootx.app.entity.SoftDetailEntity
import com.bootx.app.repository.DataBase
import com.bootx.app.repository.entity.DownloadEntity1
import com.bootx.app.repository.entity.DownloadManagerEntity
import com.bootx.app.repository.entity.HistoryEntity
import com.bootx.app.service.SoftService
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.IDownloadCallback
import com.bootx.app.util.IHttpCallback
import com.bootx.app.util.SharedPreferencesUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date


class DownloadViewModel:ViewModel() {

    private val softService = SoftService.instance()

    var adDetail by mutableStateOf<DownloadEntity1>(DownloadEntity1(id = 0, adId = ""))

    suspend fun download(context: Context,id: Int,callback: IDownloadCallback) {
        // 接口请求下载地址
        val downloadInfo = softService.download(SharedPreferencesUtils(context).get("token"), id)
        val data = downloadInfo.data
        if (downloadInfo.code == 0 && data != null && data.downloadUrl.isNotEmpty()) {
            val manager = DownloadManager.Builder(context as Activity).run {
                apkUrl(data.downloadUrl).smallIcon(R.drawable.logo)
                    .showNotification(false)
                    .showBgdToast(false)
                    .showNewerToast(false)
                apkName(data.name + ".apk").onDownloadListener(onDownloadListener = object :
                    OnDownloadListener {
                    override fun cancel() {
                        NotificationUtil.cancelNotification(context)
                    }
                    override fun done(apk: File) {
                        Log.e("DownloadViewModel", "done: ${apk.path}", )
                        NotificationUtil.showDoneNotification(
                            context, R.drawable.network_error,
                            "${data.name}下载完成,点击安装",
                            "点击安装${data.name}",
                            Constant.AUTHORITIES!!, apk
                        )
                        callback.done();
                        CoroutineScope(Dispatchers.IO).launch {
                            // 写入到数据库
                            try {
                                DataBase.getDb(context)?.getDownloadManagerDao()?.insert(
                                    DownloadManagerEntity(
                                        id = id,
                                        name = data.name,
                                        logo = data.logo,
                                        packageName = data.packageName ?: "",
                                        path = apk.path,
                                    )
                                )
                            }catch (e:Exception){
                              e.stackTrace
                            }
                        }

                    }

                    override fun downloading(max: Int, progress: Int) {
                        Log.e("DownloadViewModel", "downloading: ${max}:${progress}", )
                        val curr = (progress / max.toDouble() * 100.0).toInt()
                        val content = if (curr < 0) "" else "$curr%"
                        SharedPreferencesUtils(context).set("download_$id",content)
                        NotificationUtil.showProgressNotification(
                            context, R.drawable.network_error,
                            "${data.name}下载中...",
                            content, if (max == -1) -1 else 100, curr
                        )
                        callback.downloading(curr)
                    }

                    override fun error(e: Throwable) {
                        Log.e("DownloadViewModel", "error: ${e.toString()}", )
                        NotificationUtil.showErrorNotification(
                            context, R.drawable.network_error,
                            "${data.name} 下载出错",
                            "点击继续下载",
                        )
                        callback.error(e)
                    }

                    override fun start() {
                        Log.e("DownloadViewModel", "start: start", )
                        NotificationUtil.showNotification(
                            context, R.drawable.network_error,
                            "开始下载 ${data.name}",
                            "可稍后查看 ${data.name} 下载进度"
                        )
                        callback.start()
                    }
                })
                apkVersionName(data.versionName)
                apkSize(data.size)
                apkDescription("更新描述信息(取服务端返回数据)")
                build()
            }
            manager.download()
        }else{
            CommonUtils.toast(context, "暂无下载地址")
        }
    }

    suspend fun install(context: Context,path: String) {
        Log.d("install", "install: $path")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(
            Uri.parse("file://" + path),
            "application/vnd.android.package-archive"
        )
        context.startActivity(intent)
    }


    suspend fun download1(context: Context,id: String) {
        val res = softService.download1(SharedPreferencesUtils(context).get("token"),id)
        if (res.code == 0) {
            adDetail = res.data
        }else{
            CommonUtils.toast(context,res.msg)
        }

    }
}