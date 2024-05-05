package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.LoginEntityResponse
import com.cqzyzx.jpfx.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginService {

    @POST("/api/member/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginEntityResponse

    companion object {
        fun instance(): LoginService {
            return HiRetrofit.create(LoginService::class.java)
        }
    }
}