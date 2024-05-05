package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.repository.entity.DownloadUrlResponse
import com.cqzyzx.jpfx.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface DownloadService {

    @POST("/api/download/adReward")
    @FormUrlEncoded
    suspend fun adReward(
        @Header("token") token: String,
        @Field("id") id: Int,
        @Field("adId") adId: String,
    ): DownloadUrlResponse

    companion object {
        fun instance(): DownloadService {
            return HiRetrofit.create(DownloadService::class.java)
        }
    }
}