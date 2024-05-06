package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.CollectLogEntityListResponse
import com.cqzyzx.jpfx.entity.SoftEntity
import com.cqzyzx.jpfx.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface CollectLogService {

    @POST("/api/member/collectLog/add")
    @FormUrlEncoded
    suspend fun add(
        @Header("token") token: String,
        @Field("softId") softId: Int,
    ): BaseResponse

    @POST("/api/member/collectLog/list")
    suspend fun list(
        @Header("token") token: String,
    ): CollectLogEntityListResponse

    @POST("/api/member/collectLog/delete")
    @FormUrlEncoded
    suspend fun delete(
        @Header("token") token: String,
        @Field("softId") softId: Int,
    ): BaseResponse

    companion object {
        fun instance(): CollectLogService {
            return HiRetrofit.create(CollectLogService::class.java)
        }
    }
}
