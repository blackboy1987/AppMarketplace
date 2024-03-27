package com.bootx.app.service

import com.bootx.app.entity.BaseResponse
import com.bootx.app.entity.SoftEntity
import com.bootx.app.util.HiRetrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface SearchService {

    @POST("/api/search")
    @FormUrlEncoded
    suspend fun search(
        @Header("token") token: String,
        @Field("keywords") keywords: String,
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int
    ): SearchDataListResponse

    companion object {
        fun instance(): SearchService {
            return HiRetrofit.create(SearchService::class.java)
        }
    }
}
data class SearchDataListResponse(val data: List<SoftEntity>) : BaseResponse()
