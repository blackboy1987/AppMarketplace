package com.bootx.app.service

import com.bootx.app.entity.BaseResponse
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


data class SearchData(
    val id: Int,
    val name: String = "",
    val logo: String = "",
    val title: String = "",
    val score: String = "",
    val size: String = "",
    val versionName: String="",
    val memo: String = "",
    val updateDate: String = "",
    val avatar: String = "",
    val username: String = "",
    val rankName: String = "",
    val point: String = "",
    var isConcern: Int = 0,
)
data class SearchDataListResponse(val data: List<SearchData>) : BaseResponse()
