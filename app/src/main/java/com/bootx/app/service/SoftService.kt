package com.bootx.app.service

import com.bootx.app.entity.SoftDetailResponse
import com.bootx.app.entity.SoftListResponse
import com.bootx.app.repository.entity.DownloadEntity1Response
import com.bootx.app.repository.entity.DownloadEntityResponse
import com.bootx.app.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface SoftService {

    @POST("/api/soft/list")
    @FormUrlEncoded
    suspend fun list(
        @Header("token") token: String,
        @Field("categoryId") categoryId: Int,
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int
    ): SoftListResponse

    @POST("/api/soft/detail")
    @FormUrlEncoded
    suspend fun detail(
        @Header("token") token: String,
        @Field("id") id: String,
    ): SoftDetailResponse

    @POST("/api/soft/download")
    @FormUrlEncoded
    suspend fun download(
        @Header("token") token: String,
        @Field("id") id: Int,
    ): DownloadEntityResponse

    @POST("/api/soft/download")
    @FormUrlEncoded
    suspend fun download1(
        @Header("token") token: String,
        @Field("id") id: String,
    ): DownloadEntity1Response

    companion object {
        fun instance(): SoftService {
            return HiRetrofit.create(SoftService::class.java)
        }
    }
}