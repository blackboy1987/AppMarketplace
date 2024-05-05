package com.cqzyzx.jpfx.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.cqzyzx.jpfx.entity.LoginEntity
import com.cqzyzx.jpfx.repository.entity.UserEntity
import com.google.gson.Gson


class SharedPreferencesUtils(private val context: Context) {
    fun set(key: String,value: String){
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString(key,value)
        edit.apply()
    }
    fun setUserInfo(userInfo: UserEntity){
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        edit.putString("userInfo", Gson().toJson(userInfo))
        edit.apply()
    }
    fun getUserInfo():UserEntity{
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val userInfo = sharedPreferences.getString("userInfo", "") ?: ""
        try {
            return Gson().fromJson<UserEntity>(userInfo,UserEntity::class.java)
        }catch (e: Exception){
            return UserEntity(
                id=-1,
                username = "",
                avatar = "",
            )
        }

    }
    fun get(key: String): String {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if(key=="homeIndex"){
            return sharedPreferences.getString(key,"0") ?: "0"
        }
        return sharedPreferences.getString(key,"") ?: ""
    }

    fun remove(key: String?) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = sharedPreferences.edit()
        if(key!=null){
            edit.remove(key)
        }else{
            edit.clear()
        }
        edit.apply()
    }
}