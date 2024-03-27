package com.bootx.app.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.AppInfo
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.entity.CategoryTreeEntity
import com.bootx.app.entity.TouGaoEntity
import com.bootx.app.service.TouGaoService
import com.bootx.app.util.AppInfoUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.yysc.util.UploadUtils
import github.leavesczy.matisse.MediaResource


class TouGaoViewModel : ViewModel() {

    var touGaoService = TouGaoService.instance()

    var categories by mutableStateOf(listOf<CategoryTreeEntity>())

    var touGaoInfo by mutableStateOf(TouGaoEntity())
        private set
    var appInfo by mutableStateOf(AppInfo())
        private set

    var progress by mutableStateOf(0)
        private set

    var msg by mutableStateOf("")
        private set

    fun updateTouGaoInfo(value: Any,key: String): TouGaoEntity {
        when(key){
            "categoryId0"->touGaoInfo.categoryId0 = value as Int
            "title"->touGaoInfo.title = value as String
            "memo"->touGaoInfo.memo = value as String
            "introduce"->touGaoInfo.introduce = value as String
            "updatedContent"->touGaoInfo.introduce = value as String
        }

        return touGaoInfo
    }

    /**
     * 获取栏目
     */
    suspend fun categoryList(token: String,){
        val res = touGaoService.category(token);
        categories = res.data
    }

    suspend fun getAppInfo(context:Context,packageName: String){
        appInfo = AppInfoUtils.getAppInfo(
            context,
            packageName
        )
        touGaoInfo.title = appInfo.appName
        touGaoInfo.packageName = appInfo.packageName
        touGaoInfo.appIcon = appInfo.appIcon
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun upload(
        context: Context,
        title: String,
        memo: String,
        introduce: String,
        updatedContent: String,
        adType0: Int,
        adType1: Int,
        adType2: Int,
        adType3: Int,
        appLogo: String,
        categoryId0: Int,
        categoryId1: Int,
        quDaoIndex: Int,
        list: List<MediaResource>,
        downloadUrl: String,
        password: String,
    ) {
        val urls = arrayOfNulls<String>(list.size)
        // 先上传图片
        if(list.isNotEmpty()){
            progress = 0
            val rate = 100/list.size
            // 因为只支持单个文件上传，所以。得循环上传
            list.forEachIndexed { index, item->
                UploadUtils.uri2File(item.uri, context)?.let {
                    urls[index] = UploadUtils.uploadImage(SharedPreferencesUtils(context).get("token"), it)
                    progress += rate
                    msg = "正在上传${index+1}张图片"
                }
            }
        }
        touGaoService.create(
            SharedPreferencesUtils(context).get("token"),
            title,
            memo,
            introduce,
            updatedContent,
            adType0,
            adType1,
            adType2,
            adType3,
            appLogo,
            categoryId0,
            categoryId1,
            quDaoIndex,
            urls.joinToString(","),
            appInfo.versionCode,
            appInfo.versionName,
            appInfo.minSdkVersion,
            appInfo.targetSdkVersion,
            appInfo.appBytes,
            appInfo.packageName,
            downloadUrl,
            password,

        )


    }
}