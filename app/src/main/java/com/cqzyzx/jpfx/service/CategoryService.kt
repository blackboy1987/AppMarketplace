package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.CategoryDetailResponse
import com.cqzyzx.jpfx.entity.CategoryListResponse
import com.cqzyzx.jpfx.util.HiRetrofit
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