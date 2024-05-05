package com.cqzyzx.jpfx.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.AppInfo
import com.cqzyzx.jpfx.util.AppInfoUtils


class TouGaoListViewModel : ViewModel() {
    @SuppressLint("QueryPermissionsNeeded")
    fun getAppList(context: Context): List<AppInfo> {
        return AppInfoUtils.getAppList(context)
    }
}