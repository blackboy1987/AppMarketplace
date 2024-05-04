package com.bootx.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.entity.HomeEntity
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.yysc.service.CategoryService
import com.bootx.yysc.service.HomeService

class HomeViewModel:ViewModel() {
    private val categoryService = CategoryService.instance()
    private val homeService = HomeService.instance()
    var categoryList by mutableStateOf(listOf<CategoryEntity>(CategoryEntity(0,"全部")))
    var homeData by mutableStateOf<HomeEntity>(HomeEntity())

    suspend fun category(context:Context){
        try{
            val res = categoryService.list(SharedPreferencesUtils(context).get("token"))
            Log.e("category", "category: ${res}", )
            if (res.code == 0) {
                categoryList = res.data
            }else{
                CommonUtils.toast(context,res.msg)
            }
        }catch (e: Exception){
            Log.e("category", "category: ${e}", )
        }
    }

    suspend fun load(context:Context){
        val res = homeService.load(SharedPreferencesUtils(context).get("token"))
        if (res.code == 0) {
            homeData = res.data
        }else{
            CommonUtils.toast(context,res.msg)
        }
    }
}








