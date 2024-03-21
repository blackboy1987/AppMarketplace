package com.bootx.yysc.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.yysc.service.CategoryService

class HomeViewModel:ViewModel() {
    private val categoryService = CategoryService.instance()
    var categoryList by mutableStateOf(listOf<CategoryEntity>(CategoryEntity(0,"全部")))

    suspend fun category(context:Context){
        val res = categoryService.list(SharedPreferencesUtils(context).get("token"))
        if (res.code == 0) {
            categoryList = res.data
        }else{
            CommonUtils.toast(context,res.msg)
        }
    }
}








