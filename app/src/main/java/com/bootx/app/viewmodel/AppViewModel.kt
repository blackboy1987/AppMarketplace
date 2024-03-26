package com.bootx.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.entity.SoftEntity
import com.bootx.app.service.SoftService
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.yysc.service.CategoryService
import com.google.gson.Gson

class AppViewModel:ViewModel() {

    private val categoryService = CategoryService.instance()

    private val softService = SoftService.instance()

    var categoryLoading by mutableStateOf(true)
        private set



    var pageNumber by mutableStateOf(1)
        private set

    var softListLoading by mutableStateOf(true)
        private set



    var currentIndex by mutableStateOf(0)
        private set

    var categories = mutableListOf<CategoryEntity>()

    var softList by mutableStateOf(listOf<SoftEntity>())

    suspend fun fetchList(context: Context) {
        categoryLoading = true
        val res = categoryService.list(SharedPreferencesUtils(context).get("token"))
        val gson = Gson()
        if (res.code == 0) {
            val tmpList = mutableListOf<CategoryEntity>()
            tmpList.addAll(res.data)
            updateCurrentIndex(SharedPreferencesUtils(context).get("token"),tmpList[0].id)
            categories = tmpList
            categoryLoading = false
        } else {
            Log.e("fetchList",gson.toJson(res))
        }
    }

    suspend fun updateCurrentIndex(token: String,id: Int) {
        pageNumber = 1;
        softListLoading = true
        currentIndex = id
        val res = softService.orderBy(token,1,20,"7",id)

        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<SoftEntity>()
            if (pageNumber != 1) {
                tmpList.addAll(softList)
            }
            tmpList.addAll(res.data)
            softList = tmpList
            softListLoading = false
            pageNumber += 1
        }
    }

    suspend fun reload(token: String,) {
        softListLoading = true
        pageNumber = 1
        val res = softService.orderBy(token,1,20,"7",currentIndex)
        val gson = Gson()
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<SoftEntity>()
            tmpList.addAll(res.data)
            softList = tmpList
            softListLoading = false
            pageNumber += 1
        } else {
            Log.e("fetchList",gson.toJson(res))
        }
    }

    suspend fun loadMore(token: String,) {
        softListLoading = true
        val res = softService.orderBy(token,pageNumber,20,"7",currentIndex)
        val gson = Gson()
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<SoftEntity>()
            if (pageNumber != 1) {
                tmpList.addAll(softList)
            }
            tmpList.addAll(res.data)
            softList = tmpList
            softListLoading = false
            pageNumber += 1
        } else {
            Log.e("fetchList",gson.toJson(res))
        }
    }

    suspend fun orderBy(token: String,orderBy: String) {
        softListLoading = true
        val res = softService.orderBy(token,pageNumber,20,orderBy,currentIndex)
        val gson = Gson()
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<SoftEntity>()
            if (pageNumber != 1) {
                tmpList.addAll(softList)
            }
            tmpList.addAll(res.data)
            softList = tmpList
            softListLoading = false
            pageNumber += 1
        } else {
            Log.e("fetchList",gson.toJson(res))
        }
    }
}