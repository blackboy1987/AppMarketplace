package com.cqzyzx.jpfx.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SoftEntity(
    var id: Int=0,
    var name: String="",
    val size: String="",
    var memo: String="",
    var logo: String="",
    var updateDate: String="",
    val score: String="",
    val downloadUrl: String="",
    var images: String="",
    var versionName: String="",
    var downloads: String = "",
)

data class SoftListResponse(val data: List<SoftEntity>?) : BaseResponse()
