package com.bootx.yysc.service

import com.bootx.app.entity.HomeEntityResponse
import com.bootx.app.util.HiRetrofit
import retrofit2.http.Header
import retrofit2.http.POST


interface HomeService {

    @POST("/api/home/load")
    suspend fun load(
        @Header("token") token: String,
    ): HomeEntityResponse

    companion object {
        fun instance(): HomeService {
            return HiRetrofit.create(HomeService::class.java)
        }
    }
}