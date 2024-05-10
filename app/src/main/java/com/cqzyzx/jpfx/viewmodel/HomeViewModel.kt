package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.CategoryEntity
import com.cqzyzx.jpfx.entity.HomeEntity
import com.cqzyzx.jpfx.service.CategoryService
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils
import com.cqzyzx.jpfx.service.HomeService

class HomeViewModel:ViewModel() {
    private val categoryService = CategoryService.instance()
    private val homeService = HomeService.instance()
    var categoryList by mutableStateOf(listOf<CategoryEntity>(CategoryEntity(0,"全部")))
    var homeData by mutableStateOf<HomeEntity>(HomeEntity())
    var count by mutableStateOf(3)




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








