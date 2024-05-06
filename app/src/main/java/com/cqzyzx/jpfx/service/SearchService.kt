package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.SoftEntity
import com.cqzyzx.jpfx.util.HiRetrofit
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

    @POST("/api/hotSearch")
    suspend fun hotSearch(
        @Header("token") token: String,
    ): HotSearchDataListResponse

    companion object {
        fun instance(): SearchService {
            return HiRetrofit.create(SearchService::class.java)
        }
    }
}
data class SearchDataListResponse(val data: List<SoftEntity>) : BaseResponse()
data class HotSearchDataListResponse(val data: List<String>) : BaseResponse()
