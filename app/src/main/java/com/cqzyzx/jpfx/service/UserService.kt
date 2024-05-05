package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.CommonResponse
import com.cqzyzx.jpfx.repository.entity.UserEntity
import com.cqzyzx.jpfx.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

data class UserEntityResponse(val data: UserEntity) : BaseResponse()

interface UserService {

    @POST("/api/member/currentUser")
    suspend fun currentUser(
        @Header("token") token: String,
    ): UserEntityResponse
    @POST("/api/member/update")
    @FormUrlEncoded
    suspend fun update(@Header("token") token: String, @Field("username") username: String): CommonResponse

    companion object {
        fun instance(): UserService {
            return HiRetrofit.create(UserService::class.java)
        }
    }
}