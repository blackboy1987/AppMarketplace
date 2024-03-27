package com.bootx.app.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.AppInfo
import com.bootx.app.util.AppInfoUtils


class TouGaoListViewModel : ViewModel() {
    @SuppressLint("QueryPermissionsNeeded")
    fun getAppList(context: Context): List<AppInfo> {
        return AppInfoUtils.getAppList(context)
    }
}