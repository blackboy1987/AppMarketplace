package com.cqzyzx.jpfx.service

import com.cqzyzx.jpfx.entity.BaseResponse
import com.cqzyzx.jpfx.entity.CategoryTreeListResponse
import com.cqzyzx.jpfx.util.HiRetrofit
import com.squareup.moshi.JsonClass
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface TouGaoService {

    @POST("/api/member/touGao/category")
    suspend fun category(@Header("token") token: String): CategoryTreeListResponse

    @POST("/api/member/touGao/save")
    @FormUrlEncoded
    suspend fun create(
        @Header("token") token: String,
        @Field("title") title: String,
        @Field("updatedContent") updatedContent: String,
        @Field("adType0") adType0: Int,
        @Field("adType1") adType1: Int,
        @Field("adType2") adType2: Int,
        @Field("adType3") adType3: Int,
        @Field("appLogo") appLogo: String,
        @Field("categoryId0") categoryId0: Int,
        @Field("categoryId1") categoryId1: Int,
        @Field("quDaoIndex") quDaoIndex: Int,
        @Field("versionCode") versionCode: String,
        @Field("versionName") versionName: String,
        @Field("minSdkVersion") minSdkVersion: Int,
        @Field("targetSdkVersion") targetSdkVersion: Int,
        @Field("size") size: Long,
        @Field("packageName") packageName: String,
        @Field("downloadUrl") downloadUrl: String,
        @Field("password") password: String
    ): BaseResponse

    @POST("/api/member/touGao/list")
    @FormUrlEncoded
    suspend fun list(
        @Header("token") token: String,
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int,
        @Field("type") type: Int
    ): TouGaoEntityListResponse

    @POST("/api/member/touGao/update")
    @FormUrlEncoded
    suspend fun update(@Header("token") token: String, @Field("softId") touGaoInfoId: Int,@Field("type") type: Int): Any
    @POST("/api/member/touGao/loadInfo")
    suspend fun loadInfo(@Header("token") token: String): TouGaoInfoResponse

    companion object {
        fun instance(): TouGaoService {
            return HiRetrofit.create(TouGaoService::class.java)
        }
    }
}


@JsonClass(generateAdapter = true)
data class TouGaoEntity(
    var id: Int = 0,
    var name: String = "",
    var logo: String = "",
    var versionName: String = "",
    var createdDate: String ="",
    var statusInfo: String = "",
    var status: Int = -1,
)

data class TouGaoEntityListResponse(val data: List<TouGaoEntity>?) : BaseResponse()



@JsonClass(generateAdapter = true)
data class TouGaoInfo(
    var downloads: String = "0",
    var reviewCount: String = "0",
    var donationIcon: String = "0",
    var reviewCount1: String = "0",
)
data class TouGaoInfoResponse(val data: TouGaoInfo) : BaseResponse()