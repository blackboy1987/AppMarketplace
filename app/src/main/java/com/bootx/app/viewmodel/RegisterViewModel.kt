package com.bootx.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bootx.app.entity.LoginEntity
import com.bootx.app.service.RegisterService
import com.bootx.app.service.UserService
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils

class RegisterViewModel : ViewModel() {
    private val registerService = RegisterService.instance()
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
    var emailLoading by mutableStateOf(false)
        private set


    suspend fun register(context: Context, username: String,email: String,code: String, password: String,spreadMemberUsername: String): Boolean{
        try {
            loading = true
            val res = registerService.register(username, password,email,spreadMemberUsername)
            return if (res.code == 0 && res.data !=null) {
                data = res.data
                SharedPreferencesUtils(context).set("token",data.token)
                loading = false
                true;
            }else{
                loading = false
                CommonUtils.toast(context,res.msg);
                false
            }
        } catch (e: Throwable) {
            loading = false
            e.printStackTrace()
            Log.e("login", "login: ${e.toString()}", )
            CommonUtils.toast(context,e.toString());
            return false;
        }
    }

    suspend fun sendCode(context: Context, email: String) {
        try {
            emailLoading = true
            val res = registerService.sendCode(email)
            if(res.code==0){
                CommonUtils.toast(context,res.data)
            }else{
                CommonUtils.toast(context,res.msg)
            }
            emailLoading=false
        } catch (e: Throwable) {
            CommonUtils.toast(context,e.toString())
            emailLoading = false
        }
    }
}