package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.CommonResponse
import com.cqzyzx.jpfx.entity.LoginEntityResponse
import com.cqzyzx.jpfx.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface RegisterService {

    @POST("/api/member/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("spreadMemberUsername") spreadMemberUsername: String
    ): LoginEntityResponse

    @POST("/api/member/register/sendCode")
    @FormUrlEncoded
    suspend fun sendCode(@Field("email") email: String): CommonResponse

    companion object {
        fun instance(): RegisterService {
            return HiRetrofit.create(RegisterService::class.java)
        }
    }
}