package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.UploadFileResponse
import com.cqzyzx.jpfx.util.HiRetrofit
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface UploadService {

    @POST("/api/file/upload")
    suspend fun upload(
        @Header("token") token: String,
        @Body multipartBody: MultipartBody
    ): UploadFileResponse


    companion object {
        fun instance(): UploadService {
            return HiRetrofit.create(UploadService::class.java)
        }
    }
}