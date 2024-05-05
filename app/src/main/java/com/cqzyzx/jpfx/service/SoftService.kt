package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.CommonResponse
import com.cqzyzx.jpfx.entity.SoftDetailResponse
import com.cqzyzx.jpfx.entity.SoftListResponse
import com.cqzyzx.jpfx.repository.entity.DownloadEntity1Response
import com.cqzyzx.jpfx.repository.entity.DownloadEntityResponse
import com.cqzyzx.jpfx.repository.entity.DownloadUrlResponse
import com.cqzyzx.jpfx.util.HiRetrofit
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

    @POST("/api/soft/url")
    @FormUrlEncoded
    suspend fun getUrl(
        @Header("token") token: String,
        @Field("id") id: String,
    ): DownloadUrlResponse

    @POST("/api/soft/orderBy")
    @FormUrlEncoded
    suspend fun orderBy(
        @Header("token") token: String,
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int,
        @Field("orderBy") orderBy: String,
        @Field("categoryId") categoryId: Int = 0,
    ): SoftListResponse

    @POST("/api/soft/checkDownload")
    @FormUrlEncoded
    suspend fun checkDownload(@Header("token") token: String, @Field("id") id: String): CommonResponse

    companion object {
        fun instance(): SoftService {
            return HiRetrofit.create(SoftService::class.java)
        }
    }
}