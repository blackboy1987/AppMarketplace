package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.CategoryEntity
import com.cqzyzx.jpfx.entity.SoftEntity
import com.cqzyzx.jpfx.service.CategoryService
import com.cqzyzx.jpfx.service.SoftService
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class AppViewModel:ViewModel() {

    private val categoryService = CategoryService.instance()

    private val softService = SoftService.instance()

    var categoryLoading by mutableStateOf(true)
        private set



    var pageNumber by mutableStateOf(1)
        private set

    var softListLoading by mutableStateOf(true)
        private set
    var hasMore by mutableStateOf(true)
        private set



    var currentIndex by mutableStateOf(0)
        private set

    var categories = mutableListOf<CategoryEntity>()

    var softList by mutableStateOf(listOf<SoftEntity>())

    private var pageSize = 20

    // 分类
    suspend fun fetchList(context: Context) {
        categoryLoading = true
        val res = categoryService.list(SharedPreferencesUtils(context).get("token"))
        if (res.code == 0) {
            val tmpList = mutableListOf<CategoryEntity>()
            tmpList.addAll(res.data)
            // 加载第一个分类的数据
            updateCurrentIndex(context,1,tmpList[0].id)
            categories = tmpList
            categoryLoading = false
        }else{
            categories = arrayListOf()
        }
        categoryLoading = false
    }

    // 切换
    suspend fun updateCurrentIndex(context: Context,pageNumber1: Int,id: Int) {
        softListLoading = true
        currentIndex = id
        hasMore = true
        // 需要清除，显示loading效果
        if(pageNumber1==1){
            softList = arrayListOf()
        }


        val res = softService.orderBy(SharedPreferencesUtils(context).get("token"),pageNumber1,pageSize,"7",id)
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<SoftEntity>()
            if (pageNumber1 != 1) {
                tmpList.addAll(softList)
            }
            tmpList.addAll(res.data)
            softList = tmpList
            hasMore = res.data.size>=pageSize
            if(hasMore){
                pageNumber = pageNumber1+1
            }
        }else{
            softList = arrayListOf()
            hasMore = false
        }
        softListLoading = false
    }

    // 下拉刷新
    suspend fun reload(context: Context) {
        updateCurrentIndex(context,1,currentIndex)
    }
    // 加载更多
    suspend fun loadMore(context: Context) {
        if(hasMore){
            updateCurrentIndex(context,pageNumber,currentIndex)
        }
    }
}