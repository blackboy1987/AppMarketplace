package com.bootx.app.repository.entity

import com.bootx.app.entity.BaseResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DownloadEntity(
    var name: String,
    val size: String,
    val downloadUrl: String,
    var versionName: String,
    var packageName: String,
    var logo: String,
)

@JsonClass(generateAdapter = true)
data class DownloadEntity1(
    var id: Int,
    val adId: String,
)

data class DownloadEntityResponse(val data: DownloadEntity) : BaseResponse()
data class DownloadEntity1Response(val data: DownloadEntity1) : BaseResponse()