package com.bootx.yysc.service

import com.bootx.app.entity.CategoryDetailResponse
import com.bootx.app.entity.CategoryListResponse
import com.bootx.app.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface CategoryService {

    @POST("/api/category/list")
    suspend fun list(
        @Header("token") token: String,
    ): CategoryListResponse


    @POST("/api/category/detail")
    @FormUrlEncoded
    suspend fun detail(
        @Header("token") token: String,
        @Field("id") id: Int,
    ): CategoryDetailResponse

    companion object {
        fun instance(): CategoryService {
            return HiRetrofit.create(CategoryService::class.java)
        }
    }
}