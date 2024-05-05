package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.AppInfo
import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.CategoryTreeEntity
import com.cqzyzx.jpfx.entity.TouGaoEntity
import com.cqzyzx.jpfx.service.TouGaoService
import com.cqzyzx.jpfx.util.AppInfoUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils


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
        updatedContent: String,
        adType0: Int,
        adType1: Int,
        adType2: Int,
        adType3: Int,
        appLogo: String,
        categoryId0: Int,
        categoryId1: Int,
        quDaoIndex: Int,
        downloadUrl: String,
        password: String,
    ): BaseResponse {
        val result = touGaoService.create(
            SharedPreferencesUtils(context).get("token"),
            title,
            updatedContent,
            adType0,
            adType1,
            adType2,
            adType3,
            appLogo,
            categoryId0,
            categoryId1,
            quDaoIndex,
            appInfo.versionCode,
            appInfo.versionName,
            appInfo.minSdkVersion,
            appInfo.targetSdkVersion,
            appInfo.appBytes,
            appInfo.packageName,
            downloadUrl,
            password,
        )
        return result


    }
}