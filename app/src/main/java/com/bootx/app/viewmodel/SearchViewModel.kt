package com.bootx.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.service.SearchData
import com.bootx.app.service.SearchService
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils

class SearchViewModel:ViewModel() {

    private val searchService = SearchService.instance()

    var pageNumber by mutableStateOf(1)
        private set

    var pageSize = 20
        private set

    var loading by mutableStateOf(true)
        private set

    var list by mutableStateOf(listOf<SearchData>())

    var hasMore by mutableStateOf(true)
        private set

    suspend fun search(context: Context,keywords: String,pageNumber: Int) {
        try {
            val res = searchService.search(SharedPreferencesUtils(context).get("token"),keywords,pageNumber,pageSize)
            if (res.code == 0) {
                val tmpList = mutableListOf<SearchData>()
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
}