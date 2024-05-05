package com.cqzyzx.jpfx.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cqzyzx.jpfx.entity.LoginEntity
import com.cqzyzx.jpfx.service.LoginService
import com.cqzyzx.jpfx.service.UserService
import com.cqzyzx.jpfx.util.CommonUtils
import com.cqzyzx.jpfx.util.SharedPreferencesUtils

class LoginViewModel : ViewModel() {
    private val loginService = LoginService.instance()
    private val userService = UserService.instance()
    // 加载状态
    var loading by mutableStateOf(false)
        private set

    // 列表数据
    var data by mutableStateOf<LoginEntity>(
        LoginEntity(
            id = 0,
            username = "",
            avatar = "",
            token = "",
        )
    )


    suspend fun login(context: Context, username: String, password: String){
        try {
            loading = true
            val res = loginService.login(username, password)
            if (res.code == 0 && res.data !=null) {
                data = res.data
                val currentUser = userService.currentUser(data.token)
                SharedPreferencesUtils(context).set("userId","${currentUser.data.id}")
                SharedPreferencesUtils(context).setUserInfo(currentUser.data)
            }
            loading = false
        } catch (e: Throwable) {
            loading = false
            Log.e("login", "login: ${e.toString()}", )
            CommonUtils.toast(context,e.toString());
        }
    }
}