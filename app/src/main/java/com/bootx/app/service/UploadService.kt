package com.bootx.app.service

import com.bootx.app.entity.UploadFileResponse
import com.bootx.app.util.HiRetrofit
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