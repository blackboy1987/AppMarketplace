package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.repository.entity.UserEntity
import com.cqzyzx.jpfx.service.UserService
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class MineViewModel : ViewModel() {
    private val userService = UserService.instance()
    // 加载状态
    var loading by mutableStateOf(false)
        private set

    // 列表数据
    var data by mutableStateOf<UserEntity>(
        UserEntity(
            id = 0,
            username = "",
            avatar = "",
            upload = 0,
        )
    )


    suspend fun currentUser(context: Context){
        try {
            loading = true
            val res = userService.currentUser(SharedPreferencesUtils(context).get("token"))
            if (res.code == 0) {
                data = res.data
            }
            loading = false
        } catch (e: Throwable) {
            loading = false
            Log.e("login", "login: ${e.toString()}", )
            CommonUtils.toast(context,e.toString());
        }
    }
}