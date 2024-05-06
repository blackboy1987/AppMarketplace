package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.CollectLogEntity
import com.cqzyzx.jpfx.entity.SoftEntity
import com.cqzyzx.jpfx.service.CollectLogService
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class CollectLogViewModel:ViewModel() {

    private val collectLogService = CollectLogService.instance()


    var loading by mutableStateOf(true)
        private set

    var list by mutableStateOf(listOf<SoftEntity>())

    suspend fun list(context: Context) {
        try {
            val res = collectLogService.list(SharedPreferencesUtils(context).get("token"))
            if (res.code == 0) {
                val tmpList = mutableListOf<SoftEntity>()
                tmpList.addAll(res.data);
                list = tmpList
            }
        }catch (e: Throwable){
            CommonUtils.toast(context,"${e.message}")
        }
    }
    suspend fun add(context: Context,softId:Int) {
        try {
            val res = collectLogService.add(SharedPreferencesUtils(context).get("token"),softId)
            if (res.code == 0) {
                CommonUtils.toast(context,"${res.msg}")
            }
        }catch (e: Throwable){
            CommonUtils.toast(context,"${e.message}")
        }
    }
    suspend fun delete(context: Context,softId:Int) {
        try {
            val res = collectLogService.delete(SharedPreferencesUtils(context).get("token"),softId)
            if (res.code == 0) {
                list(context)
            }
        }catch (e: Throwable){
            CommonUtils.toast(context,"${e.message}")
        }
    }
}