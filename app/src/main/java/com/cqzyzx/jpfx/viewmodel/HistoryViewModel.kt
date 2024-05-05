package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.repository.DataBase
import com.cqzyzx.jpfx.repository.entity.HistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    var historyList by mutableStateOf(listOf<HistoryEntity>())
    suspend fun list(context: Context) {
        val historyDao = DataBase.getDb(context)?.getHistoryDao()
        CoroutineScope(Dispatchers.IO).launch {
            if (historyDao != null) {
                historyList = historyDao.getAll()
            }
        }
    }
    suspend fun remove(context: Context) {
        val historyDao = DataBase.getDb(context)?.getHistoryDao()
        CoroutineScope(Dispatchers.IO).launch {
            if (historyDao != null) {
                historyDao.delete()
                historyList = listOf()
            }
        }
    }
}