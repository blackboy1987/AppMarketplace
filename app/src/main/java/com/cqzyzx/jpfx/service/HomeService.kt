package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.HomeEntityResponse
import com.cqzyzx.jpfx.util.HiRetrofit
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