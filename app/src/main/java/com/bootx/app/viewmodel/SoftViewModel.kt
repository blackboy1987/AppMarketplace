package com.bootx.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.SoftDetailEntity
import com.bootx.app.entity.SoftEntity
import com.bootx.app.repository.entity.HistoryEntity
import com.bootx.app.service.SoftService
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.repository.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class SoftViewModel:ViewModel() {
    private val softService = SoftService.instance()
    var softList by mutableStateOf(listOf<SoftEntity>())
    var hasMore by mutableStateOf(true)

    var pageNumber by mutableIntStateOf(1)
        private set
    var pageSize by mutableIntStateOf(20)
        private set

    var softDetail by mutableStateOf<SoftDetailEntity>(SoftDetailEntity())

    var loading by mutableStateOf(false)
        private set

    suspend fun list(context: Context, pageNumber: Int,categoryId: Int): List<SoftEntity> {
        loading = true
        if(pageNumber==1){
            softList = arrayListOf()
        }
        try {
            val res = softService.list(SharedPreferencesUtils(context).get("token"),categoryId,pageNumber, pageSize)
            loading = false
            if (res.code == 0 && res.data != null) {
                val tmpList = mutableListOf<SoftEntity>()
                if (pageNumber != 1) {
                    tmpList.addAll(softList)
                }
                tmpList.addAll(res.data)
                softList = tmpList
                if (res.data.size == pageSize) {
                    hasMore = true
                    this.pageNumber += 1
                } else {
                    hasMore = false
                }
            }
        }catch (e: Throwable){
            return listOf()
        }
        loading = false
        return listOf()
    }


    suspend fun detail(context:Context,token: String,id: String): SoftDetailEntity {
        val historyDao = DataBase.getDb(context)?.getHistoryDao()
        val res = softService.detail(token,id)
        if (res.code == 0) {
            softDetail = res.data
            // 写入历史记录
            CoroutineScope(Dispatchers.IO).launch {
                if (historyDao != null) {
                    val byId = historyDao.getById(res.data.id)
                    if(byId.isEmpty()){
                        historyDao.insert(
                            HistoryEntity(
                            id=res.data.id,
                            name=res.data.name,
                            logo=res.data.logo,
                            packageName = "${res.data.fullName}",
                        )
                        )
                    }else{
                        historyDao.update(
                            HistoryEntity(
                            id=res.data.id,
                            name=res.data.name,
                            logo=res.data.logo,
                            packageName = "${res.data.fullName}",
                            updateDate = Date().time,
                        )
                        )
                    }
                }
            }
        }

        return SoftDetailEntity()
    }

    suspend fun checkDownload(context: Context, id: String): Boolean {
        val res = softService.checkDownload(SharedPreferencesUtils(context).get("token"),id)
        return res.code == 0
    }
}