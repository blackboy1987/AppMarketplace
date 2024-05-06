package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.SoftEntity
import com.cqzyzx.jpfx.service.SearchService
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class SearchViewModel:ViewModel() {

    private val searchService = SearchService.instance()

    var pageNumber by mutableStateOf(1)
        private set

    var pageSize = 20
        private set

    var loading by mutableStateOf(true)
        private set

    var list by mutableStateOf(listOf<SoftEntity>())
        private set

    var hotSearchList by mutableStateOf(listOf<String>())

    var hasMore by mutableStateOf(true)
        private set

    suspend fun search(context: Context,keywords: String,pageNumber: Int) {
        try {
            val res = searchService.search(SharedPreferencesUtils(context).get("token"),keywords,pageNumber,pageSize)
            if (res.code == 0) {
                val tmpList = mutableListOf<SoftEntity>()
                if (pageNumber != 1) {
                    tmpList.addAll(list)
                }
                tmpList.addAll(res.data)
                list = tmpList
                this.hasMore = res.data.size>=pageSize
                if(this.hasMore){
                    this.pageNumber = pageNumber+1
                }
            }
        }catch (e: Throwable){
            CommonUtils.toast(context,"${e.message}")
        }
    }

    suspend fun hotSearch(context: Context) {
        try {
            val res = searchService.hotSearch(SharedPreferencesUtils(context).get("token"))
            if (res.code == 0) {
                val tmpList = mutableListOf<String>()
                tmpList.addAll(res.data)
                hotSearchList = tmpList
            }
        }catch (e: Throwable){
            CommonUtils.toast(context,"${e.message}")
        }
    }
}